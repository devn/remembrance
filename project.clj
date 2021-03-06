(defproject remembrance "0.1.1"
  :description "Note taking"
  :url "https://github.com/mathias/remembrance"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[com.cemerick/url "0.1.0"]
                 [com.datomic/datomic-pro "0.9.4324"]
                 [com.taoensso/carmine "2.4.0"]
                 [com.taoensso/timbre "2.7.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2080"]
                 [org.clojure/data.json "0.2.3"]
                 [prismatic/dommy "0.1.2"]
                 [ring/ring-json "0.2.0"]]

  ;; clojure source code pathname
  :source-paths ["src/clj"]

  :plugins [;; cljsbuild plugin
            [lein-cljsbuild "0.3.3"]

            ;; ring plugin
            [lein-ring "0.8.7"]]

  ;; datomic configuration
  :datomic {:schemas ["resources/schema" ["remem-schema.edn"
                                          "initial-data.edn"]]}
  :profiles {:dev
             {:datomic {:config "resources/dev-transactor-template.properties"
                        :db-uri "datomic:dev://localhost:4334/remembrance"}}}

  ;; ring tasks configuration
  :ring {:handler remembrance.core/remembrance-handler
         :init remembrance.core/remembrance-init
         :auto-refresh true
         :nrepl {:start? true}}

  ;; cljsbuild tasks configuration
  :cljsbuild {:builds
              [{;; clojurescript source code path
                :source-paths ["src/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of the emitted JS file
                           :output-to "resources/public/js/application.js"

                           ;; use minimal optimization CLS directive
                           :optimizations :whitespace

                           ;; prettyfying emitted JS
                           :pretty-print true}}]})
