db.runCommand( { "storageSetConfig": {
  "stores": [{
    "s3": {
      "name": "ent-archive",           // Creates an S3 store
      "bucket": "jco-datalake",
      "region":"eu-west-2",  // Update with the bucket region code
      "prefix": "/dot_local_london/archive/"    // Update with your bucket name

    }
  }],
  "databases": {
    "history": {
        "customers": [{
          "store": "ent-archive",
          "definition": "/customers/*"
      }],
  
        "invoices": [{
            "store": "ent-archive",
            "definition": "/invoices/{year int}/*"
          }, {
            "store": "ent-archive",
            "definition": "/invoices/{year int}"
        }]
      }   
    }
  }
})