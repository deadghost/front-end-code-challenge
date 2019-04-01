(ns roomkey.app
  (:require
   [pushy.core :as push]
   [roomkey.util :refer [current-pathname]]
   [roomkey.controller.handler :refer [handle-route history]]))

(defn start []
  (handle-route (current-pathname))
  (push/start! history))
