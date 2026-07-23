# Imports Zscaler CA(s) from the Windows certificate store into Java cacerts.
# Run PowerShell as Administrator (cacerts is protected).
#
# Usage:
#   Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
#   .\scripts\import-zscaler-ca.ps1

$ErrorActionPreference = "Stop"

$javaHome = $env:JAVA_HOME
if (-not $javaHome) {
    $javaHome = "C:\Program Files\Microsoft\jdk-21.0.4.7-hotspot"
}

$keytool = Join-Path $javaHome "bin\keytool.exe"
$cacerts = Join-Path $javaHome "lib\security\cacerts"
$storePass = "changeit"
$tempDir = Join-Path $env:TEMP "zscaler-java-certs"
$aliases = @(
    "zscaler-root-ca",
    "zscaler-intermediate-ca"
)

if (-not (Test-Path $keytool)) {
    throw "keytool not found at: $keytool`nSet JAVA_HOME to your JDK or edit `$javaHome in this script."
}

if (-not (Test-Path $cacerts)) {
    throw "cacerts not found at: $cacerts"
}

New-Item -ItemType Directory -Force -Path $tempDir | Out-Null

Write-Host "JDK:     $javaHome"
Write-Host "Keytool: $keytool"
Write-Host "Store:   $cacerts"
Write-Host ""

# Prefer Zscaler root/intermediate CAs already trusted by Windows.
$zscalerCerts = Get-ChildItem Cert:\LocalMachine\Root, Cert:\LocalMachine\CA |
    Where-Object { $_.Subject -match "Zscaler" -or $_.Issuer -match "Zscaler" } |
    Sort-Object -Property @{ Expression = { if ($_.Subject -match "Root") { 0 } else { 1 } } }, Subject -Unique

if (-not $zscalerCerts) {
    Write-Host "No Zscaler CA found in Windows store. Exporting cert from api.spring.io..." -ForegroundColor Yellow

    $tcpClient = New-Object System.Net.Sockets.TcpClient("api.spring.io", 443)
    try {
        $sslStream = New-Object System.Net.Security.SslStream(
            $tcpClient.GetStream(),
            $false,
            { param($sender, $cert, $chain, $errors) return $true }
        )
        $sslStream.AuthenticateAsClient("api.spring.io")
        $zscalerCerts = @(
            New-Object System.Security.Cryptography.X509Certificates.X509Certificate2($sslStream.RemoteCertificate)
        )
    }
    finally {
        if ($sslStream) { $sslStream.Dispose() }
        $tcpClient.Close()
    }
}

$index = 0
foreach ($cert in $zscalerCerts) {
    $index++
    $alias = if ($index -le $aliases.Count) { $aliases[$index - 1] } else { "zscaler-ca-$index" }
    $certFile = Join-Path $tempDir ("{0}.cer" -f ($alias -replace '[^a-zA-Z0-9\-]', '-'))
    [IO.File]::WriteAllBytes($certFile, $cert.Export([System.Security.Cryptography.X509Certificates.X509ContentType]::Cert))
    $subject = $cert.Subject

    Write-Host "Importing [$alias]"
    Write-Host "  Subject: $subject"
    Write-Host "  File:    $certFile"

    & $keytool -list -alias $alias -keystore $cacerts -storepass $storePass 2>$null | Out-Null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  Already exists in cacerts. Skipping." -ForegroundColor Yellow
        continue
    }

    & $keytool -importcert -trustcacerts -noprompt -alias $alias -file $certFile -keystore $cacerts -storepass $storePass
    if ($LASTEXITCODE -ne 0) {
        throw "keytool failed for alias '$alias'. Run PowerShell as Administrator."
    }

    Write-Host "  Imported successfully." -ForegroundColor Green
    Write-Host ""
}

Write-Host "Done. Reload Cursor and test with:"
Write-Host '  java -cp $env:TEMP TestSSL   # if you still have the test class'
Write-Host '  or open your Spring Boot project again.'
