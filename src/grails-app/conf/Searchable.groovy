/**
 * Copyright (c) 2009/2010, Very Large Business Applications, University Oldenburg. Specifically:
 * Daniel Süpke <suepke@gmx.de>
 * Andreas Solsbach <solsbach@wi-ol.de>
 * Benjamin Wagner vom Berg <wagnervomberg@wi-ol.de>
 * Prof. Dr. Jorge Marx Gomez <jorge.marx.gomez@uni-oldenburg.de>
 * Christian Wenke <cw81@cw81.de>
 * Desislava Dechkova <desislavamd@yahoo.com>
 * Edzard Fisch <edzard.fisch@googlemail.com>
 * Frank Gerken <frank.gerken@uni-oldenburg.de>
 * Gerrit Meerpohl <gerrit.meerpohl@uni-oldenburg.de>
 * Irene Fröhlich <froehlich.irene@web.de>
 * Kerem-Kazim Sezer <Kerem.Sezer@gmx.de>
 * Olaf Roeder <o.roeder@gmx.net>
 * Oliver Norkus <oliver.norkus@googlemail.com>
 * Rachid Lacheheb <rachid.lacheheb@mail.uni-oldenburg.de>
 * Sebastian van Vliet <sebastian.van.vliet@mail.uni-oldenburg.de>
 * Swetlana Lipnitskaya <swetlana.lipnitskaya@uni-oldenburg.de>
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
 
/**
 * This {@link groovy.util.ConfigObject} script provides Grails Searchable Plugin configuration.
 *
 * You can use the "environments" section at the end of the file to define per-environment
 * configuration.
 *
 * Note it is NOT required to add a reference to this file in Config.groovy; it is loaded by
 * the plugin itself.
 *
 * Available properties in the binding are:
 *
 * @param userHome The current user's home directory.
 *                 Same as System.properties['user.home']
 * @param appName The Grails environment (ie, "development", "test", "production").
 *                Same as System.properties['grails.env']
 * @param appVersion The version of your application
 * @param grailsEnv The Grails environment (ie, "development", "test", "production").
 *                  Same as System.properties['grails.env']
 *
 * You can also use System.properties to refer to other JVM properties.
 *
 * This file is created by "grails install-searchable-config", and replaces
 * the previous "SearchableConfiguration.groovy"
 */
searchable {

    /**
     * The location of the Compass index
     *
     * Examples: "/home/app/compassindex", "ram://app-index" or null to use the default
     *
     * The default is "${user.home}/.grails/projects/${app.name}/searchable-index/${grails.env}"
     */
    compassConnection = new File(
        "${userHome}/.grails/projects/${appName}/searchable-index/${grailsEnv}"
    ).absolutePath

    /**
     * Any settings you wish to pass to Compass
     *
     * Use this to configure custom/override default analyzers, query parsers, eg
     *
     *     Map compassSettings = [
     *         'compass.engine.analyzer.german.type': 'German'
     *     ]
     *
     * gives you an analyzer called "german" you can then use in mappings and queries, like
     *
     *    class Book {
     *        static searchable = { content analyzer: 'german' }
     *        String content
     *    }
     *
     *    Book.search("unter", analyzer: 'german')
     *
     * Documentation for Compass settings is here: http://www.compass-project.org/docs/2.1.0M2/reference/html/core-settings.html
     */
    compassSettings = [:]

    /**
     * Default mapping property exclusions
     *
     * No properties matching the given names will be mapped by default
     * ie, when using "searchable = true"
     *
     * This does not apply for classes using "searchable = [only/except: [...]]"
     * or mapping by closure
     */
    defaultExcludedProperties = ["password"]

    /**
     * Default property formats
     *
     * Value is a Map between Class and format string, eg
     *
     *     [(Date): "yyyy-MM-dd'T'HH:mm:ss"]
     *
     * Only applies to class properties mapped as "searchable properties", which are typically
     * simple class types that can be represented as Strings (rather than references
     * or components) AND only required if overriding the built-in format.
     */
    defaultFormats = [:]

    /**
     * Set default options for each SearchableService/Domain-class method, by method name.
     *
     * These can be overriden on a per-query basis by passing the method a Map of options
     * containing those you want to override.
     *
     * You may want to customise the options used by the search method, which are:
     *
     * @param reload          whether to reload domain class instances from the DB: true|false
     *                        If true, the search  will be slower but objects will be associated
     *                        with the current Hibernate session
     * @param escape          whether to escape special characters in string queries: true|false
     * @param offset          the 0-based hit offset of the first page of results.
     *                        Normally you wouldn't change it from 0, it's only here because paging
     *                        works by using an offset + max combo for a specific page
     * @param max             the page size, for paged search results
     * @param defaultOperator if the query does not otherwise indicate, then the default operator
     *                        applied: "or" or "and".
     *                        If "and" means all terms are required for a match, if "or" means
     *                        any term is required for a match
     * @param suggestQuery    if true and search method is returning a search-result object
     *                        (rather than a domain class instance, list or count) then a
     *                        "suggestedQuery" property is also added to the search-result.
     *                        This can also be a Map of options as supported by the suggestQuery
     *                        method itself
     *
     * For the options supported by other methods, please see the documentation
     * http://grails.org/Searchable+Plugin
     */
    defaultMethodOptions = [
        search: [reload: false, escape: false, offset: 0, max: 10, defaultOperator: "and"],
        suggestQuery: [userFriendly: true]
    ]

    /**
     * Should changes made through GORM/Hibernate be mirrored to the index
     * automatically (using Compass::GPS)?
     *
     * If false, you must manage the index manually using index/unindex/reindex
     */
    mirrorChanges = true

    /**
     * Should the database be indexed at startup (using Compass:GPS)?
     *
     * Possible values: true|false|"fork"
     *
     * The value may be a boolean true|false or a string "fork", which means true,
     * and fork a thread for it
     *
     * If you use BootStrap.groovy to insert your data then you should use "true",
     * which means do a non-forking, otherwise "fork" is recommended
     */
    bulkIndexOnStartup = true

    /**
     * Should index locks be removed (if present) at startup?
     */
    releaseLocksOnStartup = true
}

// per-environment settings
environments {
    development {
        searchable {
            // development is default; inherits from above
        }
    }

    test {
        searchable {
            // disable bulk index on startup
            bulkIndexOnStartup = false

            // use faster in-memory index
            compassConnection = "ram://test-index"
        }
    }

    production {
        searchable {
            // add your production settings here
        }
    }
}
