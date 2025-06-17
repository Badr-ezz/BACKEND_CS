to import data to your db use those steps : 

1 - first : 
Place the dataset in the temp Folder in the docker container 

2 - second : 

import it via mongoimport
```powershell
docker exec mongodb mongoimport `
  --db MyImportDB `
  --collection persons `
  --file /tmp/persons.json `
  --username "admin" `
  --password  "P@ssw0rd!123" `
  --authenticationDatabase admin
```

username and password are credentials to acces to the db 


## Here a script to import multiple collections : All you need is to specify the collections names and the right path and credentials 
```powershell
<#
.SYNOPSIS
    Imports multiple JSON collections into MongoDB Docker container
.DESCRIPTION
    Copies JSON files to container and imports them into specified collections
.EXAMPLE
    .\import-mongodb-collections.ps1
#>

# Configuration
$containerName = "mongodb"
$databaseName = "MyImportDB"
$mongoUsername = "rootuser"
$mongoPassword = "rootpass"
$dataDirectory = "C:\Users\Osaka Gaming Maroc\Desktop\dataset"

# Collections configuration
$collections = @(
    @{
        Name = "persons"
        JsonFile = "$dataDirectory\persons.json"
    },
    @{
        Name = "teachers"
        JsonFile = "$dataDirectory\teachers.json"
    },
    @{
        Name = "courses"
        JsonFile = "$dataDirectory\courses.json"
    }
)

# Verify container is running
$containerStatus = docker inspect -f '{{.State.Running}}' $containerName 2>$null
if ($containerStatus -ne "true") {
    Write-Host "[ERROR] MongoDB container '$containerName' is not running!" -ForegroundColor Red
    exit 1
}

# Process each collection
foreach ($collection in $collections) {
    $collectionName = $collection.Name
    $localJsonFile = $collection.JsonFile
    $tempPath = "/tmp/$(Split-Path $localJsonFile -Leaf)"

    # Verify file exists
    if (-not (Test-Path $localJsonFile)) {
        Write-Host "[ERROR] File not found: $localJsonFile" -ForegroundColor Red
        continue
    }

    # 1. Copy JSON file to container
    Write-Host "`n[COPY] Copying $localJsonFile to container..." -ForegroundColor Cyan
    docker cp $localJsonFile "${containerName}:${tempPath}"

    if (-not $?) {
        Write-Host "[ERROR] Failed to copy $localJsonFile to container!" -ForegroundColor Red
        continue
    }

    # 2. Import data
    Write-Host "[IMPORT] Importing into collection '$collectionName'..." -ForegroundColor Cyan
    Write-Host "[PATH] Temporary container path: $tempPath" -ForegroundColor Yellow
    
    docker exec $containerName mongoimport `
        --db $databaseName `
        --collection $collectionName `
        --file $tempPath `
        --username $mongoUsername `
        --password $mongoPassword `
        --authenticationDatabase admin

    if ($?) {
        Write-Host "[SUCCESS] Imported $($collection.JsonFile) to $collectionName" -ForegroundColor Green
    }
    else {
        Write-Host "[ERROR] Failed to import $($collection.JsonFile)" -ForegroundColor Red
    }
}

Write-Host "`n[COMPLETE] All operations completed!" -ForegroundColor Green
```
