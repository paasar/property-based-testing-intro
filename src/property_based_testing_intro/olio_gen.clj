(ns property-based-testing-intro.olio-gen
  (:require [clojure.test.check.generators :as gen]))

(def names-part-1 ["Cataclysmic" "Combustible" "Noxious" "Torrential" "Verdurous" "Merciful" "Fervent" "Glorified" "Indomitable" "Mindful" "Ancient" "Belligerent" "Thrashing" "Brazen" "Champion of" "Colossal" "Cryptic" "Dauntless" "Defiant" "Duskborne" "Electrostatic" "Ravenous" "Regal" "Solitary" "Sparring" "Stalking" "Stampeding" "Steadfast" "Sun-Blessed" "Tattered" "Territorial" "Verdant" "Vicious" "Visionary" "Vizier of the" "Walking" "Watchful" "Wily" "Wretched" "Zealot of the"])

(def names-part-2 ["Gearhulk" "Oketra" "Kefnet" "Bontu" "Hazoret" "Rhonas" "Tyrant" "Khenra" "Pummeler" "Etali" "Ghalta" "Paladin" "Forerunner" "Aetherborn" "Gishath" "Gonti" "Hapatra" "Hydra" "Apprentice" "Jace" "Liliana" "Gideon" "Chandra" "Nissa" "Kari Zev" "Kinjalli" "Tilonalli" "Cub" "Branchwalker" "Mimic" "Mummy" "Nezahal" "Otepec" "Padeem" "Ramunap" "Phoenix" "Raptor" "Khenra" "Gremlin" "Goblin" "Skymarcher" "Cutthroat" "Champion" "Oracle" "Dragon" "Naga" "Kavu" "Thopter" "Camel" "Mage" "Tetzimoc" "God" "Rats" "Tocatli" "Prophet" "Ballista" "Yahenni" "Zacama" "Zetalpa"])

(def name-gen (gen/return "Olio"))

(def cost-gen (gen/return [1 :r]))

(def color-gen (gen/return [100 100 100]))

(def shape-gen (gen/return :circle))

(def picture-gen (gen/return {:background-color [0 255 0]
                              :head-shape :circle
                              :head-color [255 0 0]
                              :body-shape :circle
                              :body-color [0 0 255]}))

(def power-and-toughness-gen (gen/return "0/1"))

(def olio-gen (gen/fmap (fn [[name cost picture power-and-toughness]]
                          {:name name
                           :cost cost
                           :picture picture
                           :power-and-toughness power-and-toughness})
                        (gen/tuple name-gen cost-gen picture-gen power-and-toughness-gen)))

(defn generate-olio []
  (gen/generate olio-gen))
