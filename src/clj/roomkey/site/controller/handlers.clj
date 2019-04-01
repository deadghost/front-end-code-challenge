(ns roomkey.site.controller.handlers
  (:require [roomkey.site.views.pages :as page]))

(def handler-table
  {:home page/home
   :hotels page/home
   :hotel page/home})
