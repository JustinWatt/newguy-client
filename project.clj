(defproject newguy-client "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [reagent "0.5.1"]
                 [re-frame "0.4.1"]
                 [re-com "0.6.1"]
                 [secretary "1.2.3"]
                 [timothypratley/reanimated "0.1.1"]
                 [expectations "2.0.9"]
                 [com.rpl/specter "0.8.0"]
                 [cljs-ajax "0.5.2"]
                 [com.cognitect/transit-cljs "0.8.232"]]

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.4.0"]  ]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js" ]

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.10"] ]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}


  :figwheel {:css-dirs ["resources/public/css"]
             :nrepl-port 7888
             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl" ]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]

                        :figwheel {:on-jsload "newguy-client.core/mount-root"}

                        :compiler {:main newguy-client.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :notify-command ["phantomjs" "test/unit-test.js" "test/unit-test.html"]
                        :compiler {:optimizations :whitespace
                                   :pretty-print true
                                   :output-to "test/js/app_test.js"
                                   :warnings {:single-segment-namespace false}}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main newguy-client.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]})
