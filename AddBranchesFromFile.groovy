import groovy.json.JsonSlurper

def inputFile = new File("SwAccountLocations2.js")
def InputJSON = new JsonSlurper().parseText(inputFile.text)
Collection collect = InputJSON.findAll { it.key != "Comments" }.collect { it.key }
collect.forEach{dbName -> AddNewBranch.createBranch(dbName) }
