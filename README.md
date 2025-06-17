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
