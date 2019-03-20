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
                 [bidi "2.1.2"] ; Routing
                 ]
  :source-paths ["src/clj" "src/cljc"]
  :target-path "target/%s"
  :plugins [[lein-garden "0.3.0"]]
  :garden {:builds [{:id "app"
                     :source-paths ["src/clj"]
                     :stylesheet roomkey.site.css.app/app-stylesheet
                     :compiler {:output-to
                                "resources/public/css/stylesheet.css"
                                :pretty-print? false}}]}
  :profiles
  {:uberjar {:aot :all}
   :dev {}
   :prod {}})
