(ns roomkey.subs.util
  (:require [re-frame.core :refer [register-sub]])
  (:require-macros [reagent.ratom :refer [reaction]]))

(register-sub
 :util/db
 (fn [db [_]]
   (reaction @db)))
