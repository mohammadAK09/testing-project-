param(
    [Parameter(Mandatory=$true)][ValidateSet("1","2")][string]$Milestone,
    [Parameter(Mandatory=$true)][string]$Report,
    [Parameter(Mandatory=$true)][string]$Manifest
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $Report -PathType Leaf)) { throw "Missing report: $Report" }
if (-not (Test-Path $Manifest -PathType Leaf)) { throw "Missing manifest: $Manifest" }

git rev-parse --is-inside-work-tree | Out-Null
if ((git status --porcelain).Length -gt 0) {
    throw "Working tree has uncommitted changes. Commit or remove them first."
}

$out = "submission/milestone-$Milestone"
$zip = "submission/milestone-$Milestone-submission.zip"
Remove-Item $out -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item $zip -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path $out -Force | Out-Null

git bundle create "$out/repository.bundle" --all
git bundle verify "$out/repository.bundle"
Copy-Item $Report "$out/report.pdf"
Copy-Item $Manifest "$out/submission-manifest.txt"
Compress-Archive -Path "$out/*" -DestinationPath $zip
Write-Host "Created $zip"
