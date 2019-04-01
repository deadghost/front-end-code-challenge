(ns roomkey.views.components.top-nav)

(defn top-nav []
  [:nav {:class ["flex" "items-center" "content-center"
                 "pl-6" "p-2" "bg-blue" "text-white"]}
   [:a {:href "/"
        :class ["flex-auto"]}
    [:img {:class ["w-32"]
           :src "/imgs/invert-logo.png"
           #_(str "https://d75rs6siber1y.cloudfront.net/ea590c2680/"
                  "jakarta/main_site/img/logo-legacy.svg")}]]
   [:div 
    [:a {:class "p-3"} "$ USD"]
    [:a {:class "p-3"} "Register"]
    [:a {:class "p-3"} "Log In"]]])
