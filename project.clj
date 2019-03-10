(defproject roomkey "0.1.0-SNAPSHOT"
  :description "Room Key frontend challenge."
  :url "https://www.roomkey.com/"
  :dependencies [;; Clojure Dependencies
                 [org.clojure/clojure "1.10.0"]
                 [garden "1.3.2"] ; CSS
                 [org.immutant/web "2.1.9"] ; Server
                 [io.pedestal/pedestal.service "0.5.4"]
                 [io.pedestal/pedestal.immutant "0.5.4"]
                 [ring/ring-defaults "0.2.1"] ; Middleware
                 ;; Clojurescript Dependencies
                 [org.clojure/clojurescript "1.9.854"]
                 [reagent "0.6.1"] ; react
                 [re-frame "0.10.1"] ; reagent framework
                 [prismatic/dommy "1.1.0"] ; Selectors
                 [bidi "2.1.2"] ; Routing
                 [kibu/pushy "0.3.8"] ; URL history
                 ]
  :source-paths ["src/clj"]
  :target-path "target/%s"
  :plugins [[lein-garden "0.3.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.13"]]
  :figwheel {:css-dirs ["resources/public/css"]}
  :garden {:builds [{:id "app"
                     :source-paths ["src/clj"]
                     :stylesheet roomkey.css/stylesheet
                     :compiler {:output-to
                                "resources/public/css/stylesheet.css"
                                :pretty-print? false}}]}
  :profiles
  {:uberjar {:aot :all}
   :dev {:cljsbuild
         {:builds
          [{:id "app"
            :source-paths ["src/cljs"]
            :compiler {:output-to "resources/public/js/app.js"
                       :output-dir "resources/public/js/out"
                       :asset-path "/js/out"
                       :main "roomkey.controller.main"
                       :pretty-print false
                       :source-map true
                       :optimizations :none}
            :notify-command ["notify-send"]
            :figwheel true}]}}
   :prod {:cljsbuild
          {:builds
           [{:id "app"
             :source-paths ["src/cljs"]
             :compiler {:output-to "resources/public/js/app.js"
                        :output-dir "resources/public/js/out"
                        :asset-path "/js/out"
                        :main "roomkey.controller.main"
                        :pretty-print false
                        :optimizations :advanced}}]}}})
