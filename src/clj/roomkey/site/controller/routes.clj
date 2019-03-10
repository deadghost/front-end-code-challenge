(ns roomkey.site.controller.routes
  (:require
   [bidi.bidi :as bidi]
   [roomkey.site.views.pages :as page]))

(def routes
  ["/" :index])

(def handler-table
  {:index page/index})
