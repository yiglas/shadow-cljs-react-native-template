(ns core
  (:require [reagent.core :as r :refer [atom]]
            ["react-native" :as rn :refer [AppRegistry]]))

(enable-console-print!)
(rn/YellowBox.ignoreWarnings #js ["Warning: componentWillMount"
                                  "Remote debugger"])

(defonce component-to-update (atom nil))

(defn content []
  [:> rn/Text {:style {:font-size 30
                       :font-weight "100"
                       :margin-bottom 20
                       :text-align "center"}}
   "Hi Shadow!"])

(defn app-root []
  [:> rn/SafeAreaView {:style {:flex-direction "column"}
                       :flex 1
                       :margin 5
                       :background-color "white"}
   [content]])


(def updatable-app-root
  (with-meta app-root
    {:component-did-mount
     (fn [] (this-as ^js this
                     (reset! component-to-update this)))}))

(defn reload []
  (println "reloading" updatable-app-root)
  (.forceUpdate ^js @component-to-update))

(defn init []
  (.registerComponent AppRegistry
                      "Scoreboard"
                      #(r/reactify-component updatable-app-root)))
