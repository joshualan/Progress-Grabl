package io.gitlab.grabl

import org.gradle.api.tasks.Input
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Optional

class CreateDatabase extends DefaultTask {

    // If there's an easier way to do this WHILE keeping the hard-typing
    // functionality, let aestrada@progress.com know
    @Input
    String dbName

    @Input @Optional
    String destDir = null

    @Input @Optional
    String sourceDb = null

    @Input @Optional
    String schemaFile = null

    @Input @Optional
    String structFile = null

    @Input @Optional
    int blockSize = 0

    @Input @Optional
    boolean noInit = null

    @Input @Optional
    String codepage = null

    @Input @Optional
    String wordRules = null

    @Input @Optional
    boolean multiTenant = null

    @Input @Optional
    boolean failOnError = null

    @Input @Optional
    String collation = null

    @Input @Optional
    String tempDir = null

    @Input @Optional
    String cpInternal = null

    @Input @Optional
    String cpStream = null

    @Input @Optional
    String cpCase = null

    @Input @Optional
    String cpColl = null

    @Input @Optional
    boolean newInstance = null 

    @Input @Optional
    boolean largeFiles = null

    @Input @Optional
    boolean relative = null 
        
    @Input @Optional
    boolean auditing = null 

    @Input @Optional
    String auditArea = null
    
    @Input @Optional
    String auditIndexArea = null

    @TaskAction
    def createDB() {
        Map args = [:]

        if (destDir) {
            new File(destDir).mkdirs()
        }

        args.put('dbname', dbName)
        args.put('destDir', destDir)          
        args.put('sourceDb', sourceDb)
        args.put('schemaFile', schemaFile)
        args.put('structFile', structFile)
        args.put('blockSize', blockSize)
        args.put('noInit', noInit)
        args.put('codepage', codepage)
        args.put('wordRules', wordRules)
        args.put('multiTenant', multiTenant)
        args.put('failOnError', failOnError)
        args.put('collation', collation)
        args.put('tempDir', tempDir)
        args.put('cpInternal', cpInternal)
        args.put('cpStream', cpStream)
        args.put('cpCase', cpCase)
        args.put('cpColl', cpColl)
        args.put('newInstance', newInstance)
        args.put('largeFiles', largeFiles)
        args.put('relative', relative)
        args.put('auditing', auditing)
        args.put('auditArea', auditArea)
        args.put('auditIndexArea', auditIndexArea)

        // Sort out all the nulls since we wanna leave the defaults to PCT
        def tmp = args.findAll { it.value }

        // This is shorthand for something like:
        //   ant.PCTCreateBase(destDir: dbDir, dbName: 'testfoo', largeFiles: true)
        // but we use the spread map operator in groovy.
        ant.PCTCreateBase(*:tmp)
    }

    protected GrablExtension getExt() {
        return project.extensions.getByType(GrablExtension)
    }
}
