(ns roomkey.app
  (:require
   [pushy.core :as push]
   [roomkey.controller.handler
    :refer [handle-route current-pathname history]]))

(defn start []
  (handle-route (current-pathname))
  (push/start! history))
