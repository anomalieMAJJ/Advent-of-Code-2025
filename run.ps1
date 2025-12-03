<#
run.ps1 - compile and run a specific Day/Part for the Advent-of-Code project

Usage:
  .\run.ps1 1 1                # run day01 part1 (uses inputs/day01/input.txt)
  .\run.ps1 day01 2           # run day01 part2
  .\run.ps1 3 1 .\path\to\input.txt  # run day03 part1 with explicit input path

Notes:
 - Requires Java (javac/java) on PATH.
 - Script compiles the single source file to ./bin and runs it.
#>


param(
    [Parameter(Position=0)]
    [string]$Day = "1",
    [Parameter(Position=1)]
    [int]$Part = 1,
    [Parameter(Position=2)]
    [string]$InputPath = ""
)

function Pad-DayNumber([string]$d) {
    # Accept formats: 1, 01, day1, day01
    if ($d -match '^(?:day)?0*(\d+)$') {
        return ('{0:D2}' -f [int]$matches[1])
    }
    throw "Invalid day format: $d. Use 1 or day01 or 01"
}

try {
    $dayPadded = Pad-DayNumber $Day
} catch {
    Write-Error $_.Exception.Message
    exit 1
}

$srcFile = "src\day$dayPadded\Day${dayPadded}Part$Part.java"
$className = "day$dayPadded.Day${dayPadded}Part$Part"
$binDir = "bin"

if (-not (Test-Path $srcFile)) {
    Write-Error "Source file not found: $srcFile"
    exit 1
}

if (-not (Test-Path $binDir)) { New-Item -ItemType Directory -Path $binDir | Out-Null }

Write-Host "Compiling $srcFile -> $binDir..."
$javac = "javac"
& $javac -d $binDir $srcFile
$exitCode = $LASTEXITCODE
if ($exitCode -ne 0) {
    Write-Error "Compilation failed (exit $exitCode)."
    exit $exitCode
}

if ([string]::IsNullOrWhiteSpace($InputPath)) { $inputPath = "inputs\day$dayPadded\input.txt" } else { $inputPath = $InputPath }

Write-Host "Running $className with input: $inputPath"
$java = "java"
& $java -cp $binDir $className $inputPath
