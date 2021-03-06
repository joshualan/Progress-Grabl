package io.gitlab.grabl

import org.gradle.api.Project
import org.gradle.api.file.FileCollection

/**
 * Global default grabl plugin configuration.
 *
 * These settings are applied to relevant tasks created by grabl.
 */
class GrablExtension {
    /**
     * Name of the property on Project this instance should be bound to.
     */
    final static String NAME = 'abl'

    /**
     * The default name of the rcode directory for all compilations.
     * Relative to {@link org.gradle.api.Project#getBuildDir()} ({@value}).
     */
    final static String DEFAULT_RCODE_DIR_NAME = 'rcode'

    /**
     * The default rcode directory for all compilations.
     */
    Object rcodeDir

    /**
     * The default PROPATH for this project.
     */
    FileCollection propath

    /**
     * The default database connections for this project.
     */
    Set<String> dbConnections = []

    /**
     * Default parameters to pass to ALL PCT tasks.
     */
    Map pctTaskArgs = [:]

    /**
     * Stores private reference to project so {@code rcodeDir} can be
     * resolved dynamically when accessed.
     */
    private Project project

    GrablExtension(Project project) {
        this.project = project

        /* Set initial value to a closure so that it is re-evaluated on
         * every access (see {@link #getRcodeDir} and therefore is
         * always relative to {@link Project#buildDir}.
         * It can still be set to a static string which will stop it
         * auto-updating.
         */
        rcodeDir = {
            new File(project.buildDir, this.DEFAULT_RCODE_DIR_NAME)
        }
        propath = project.files('src/main/abl')
    }

    File getRcodeDir() {
        return project.file(rcodeDir)
    }

    /**
     * Set default rcode directory
     *
     * The given directory will be evaluated as per
     * {@link Project.file(Object)} on retrieval.
     *
     * @param rd directory to use for rcode
     */
    void rcodeDir(Object rd) {
        this.rcodeDir = rd
    }

    /**
     * Add some paths to propath of all grabl tasks
     *
     * The given paths will be evaluated as per
     * {@link Project.files(Object...)}.
     *
     * @param paths the paths to add
     */
    void propath(Object... paths) {
        this.propath = project.files(paths)
    }

    /**
     * Add some database connection references to all grabl tasks
     *
     * @param dbs an iterable of names/aliases of databases to add
     */
    void dbConnections(String... dbs) {
        dbConnections.addAll(dbs)
    }

    /**
     * Configure parameters to pass to ALL PCT tasks via a closure
     *
     * The closure is called with its delegate set to the pctTaskArgs
     * map which allows a handy way of setting multiple arguments, e.g.
     * {@code pctTaskArgs { preprocess = true; listing = true }}
     *
     * @param cfg the closure with configuration assignments
     */
    void pctTaskArgs(Closure cfg) {
        project.configure(pctTaskArgs, cfg)
    }
}
