(ns roomkey.site.controller.routes
  (:require [bidi.bidi :as bidi]))

(def routes
  ["/"
   {"" :home
    ["locations/" :location-id "/hotels"] :hotels
    ["hotels/" :hotel-id] :hotel}])
