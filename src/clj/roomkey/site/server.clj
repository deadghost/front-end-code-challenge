(ns roomkey.site.server
  (:require
   [bidi.ring :refer [make-handler]]
   [immutant.web :as web]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [roomkey.site.controller.routes :refer [routes]]
   [roomkey.site.controller.handlers :refer [handler-table]]))

(def site
  (-> (make-handler routes handler-table)
      (wrap-defaults site-defaults)))

(defn run []
  (web/run site
    {:host "localhost"
     :port 1238
     :path "/"
     :virtual-host ["localhost"]}))

(def -main run)
