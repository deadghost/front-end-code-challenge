(ns roomkey.site.css.app
  (:require [garden.core :refer [css]]
            [garden.def :refer [defstylesheet defstyles defkeyframes]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :as sel]))

(def bg-blue
  (let [color "#5c5cf7"]
    [[:.bg-blue {:background color}]
     [:.hover:bg-blue:hover {:background color}]]))

(def bg-dark-blue-trans
  [:.bg-dark-blue-trans {:background "rgba(73,73,196,0.9)"}])

(defn index-page []
  [[:.hero {:background-image "url(/imgs/gili-t.jpg)"
            :min-height "565px"}]
   ;; This is behavior/interactive CSS. It *might* be possible to make this a
   ;; utility class.
   [:#guests-rooms-dropdown {:display "none"}]
   ["#guests-rooms-toggle:checked + #guests-rooms-dropdown"
    {:display "block"}]

   [:#guests-rooms {:color "#958E9C"}]])

(defn stylesheet []
  [(index-page)
   bg-blue
   bg-dark-blue-trans])

(def app-stylesheet (stylesheet))

