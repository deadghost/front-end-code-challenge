(ns roomkey.site.css.app
  (:require [garden.core :refer [css]]
            [garden.def :refer [defstylesheet defstyles defkeyframes]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :as sel]))

(def bg-blue
  [:.bg-blue {:background "#5c5cf7"}])

(def bg-dark-blue-trans
  [:.bg-dark-blue-trans {:background "rgba(73,73,196,0.9)"}])

(defn index-page []
  [[:.hero {:background-image "url(/imgs/gili-t.jpg)"
            :min-height "565px"}]
   ;; These are behavior/interactive CSS. It *might* be possible to make these a
   ;; utility class.
   [:#location-search-dropdown {:display "none"}]
   ["#location-search:focus + #location-search-dropdown"
    {:display "block"}]
   [:#guests-rooms-dropdown {:display "none"}]
   ["#guests-rooms-toggle:checked + #guests-rooms-dropdown"
    {:display "block"}]

   [:#guests-rooms {:color "#958E9C"}]])

(defn stylesheet []
  [(index-page)
   bg-blue
   bg-dark-blue-trans])

(def app-stylesheet (stylesheet))

