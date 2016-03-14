angular.module('app', ['chart.js', 'ui.bootstrap'])

    .directive('deckBuilder', function() {
        return {
            restrict: 'A',
            scope: true,
            controllerAs: 'ctrl',
            bindToController: {
                deckId: '@'
            },
            controller: function($http) {
                var ctrl = this;

                ctrl.deckId = 0;

                ctrl.filter = {
                    name: '',

                    classSelector: 'all',
                    neutral: true,

                    typeSelector: {
                        minion: false,
                        spell: false,
                        weapon: false,
                        minionType: 'all'
                    },

                    effectSelector: 'none',

                    raritySelector: {
                        free: false,
                        common: false,
                        rare: false,
                        epic: false,
                        legendary: false
                    },

                    filter: function(cards) {
                        var result = {};
                        for(var id in cards) {
                            if (cards.hasOwnProperty(id)) {
                                var card = cards[id];
                                if(
                                    !ctrl.filter.filterByName(card)
                                    || !ctrl.filter.filterByClass(card)
                                    || !ctrl.filter.filterByType(card)
                                    || !ctrl.filter.filterByEffect(card)
                                    || !ctrl.filter.filterByRarity(card)
                                ) {
                                    continue;
                                }
                                result[id] = card;
                            }
                        }
                        return result;
                    },

                    filterByName: function(card) {
                        return ctrl.filter.name == '' ? true : card.name.indexOf(ctrl.filter.name) > -1;
                    },

                    filterByClass: function(card) {
                        if(0 == card.classId) {
                            return ctrl.filter.neutral;
                        }
                        if('all' == ctrl.filter.classSelector) {
                            return true;
                        }
                        return ctrl.filter.classSelector == card.classId;
                    },

                    filterByType: function(card) {
                        if(ctrl.filter.typeSelector.minion
                            || ctrl.filter.typeSelector.spell
                            || ctrl.filter.typeSelector.weapon) {
                            if('Minion' == card.type && !ctrl.filter.typeSelector.minion) {
                                return false;
                            }
                            if('Spell' == card.type && !ctrl.filter.typeSelector.spell) {
                                return false;
                            }
                            if('Weapon' == card.type && !ctrl.filter.typeSelector.weapon) {
                                return false;
                            }
                        }

                        return 'all' == ctrl.filter.typeSelector.minionType
                            || card.raceId == ctrl.filter.typeSelector.minionType;
                    },

                    filterByEffect: function(card) {
                        switch(ctrl.filter.effectSelector) {
                            case 'none': return true;
                            case 'aura': return card.aura;
                            case 'battleCry': return card.battleCry;
                            case 'charge': return card.charge;
                            case 'combo': return card.combo;
                            case 'deathRattle': return card.deathRattle;
                            case 'divineShield': return card.divineShield;
                            case 'enrage': return card.enrage;
                            case 'freeze': return card.freeze;
                            case 'inspire': return card.inspire;
                            case 'ogre': return card.ogre;
                            case 'overload': return card.overload;
                            case 'poison': return card.poison;
                            case 'secret': return card.secret;
                            case 'spellpower': return card.spellpower;
                            case 'stealth': return card.stealth;
                            case 'taunt': return card.taunt;
                            case 'windfury': return card.windfury;
                        }
                    },

                    filterByRarity: function(card) {
                        if(!ctrl.filter.raritySelector.free
                            && !ctrl.filter.raritySelector.common
                            && !ctrl.filter.raritySelector.rare
                            && !ctrl.filter.raritySelector.epic
                            && !ctrl.filter.raritySelector.legendary) {
                            return true;
                        }
                        if(0 == card.qualityId && !ctrl.filter.raritySelector.free) {
                            return false;
                        }
                        if(1 == card.qualityId && !ctrl.filter.raritySelector.common) {
                            return false;
                        }
                        if(2 == card.qualityId && !ctrl.filter.raritySelector.rare) {
                            return false;
                        }
                        if(3 == card.qualityId && !ctrl.filter.raritySelector.epic) {
                            return false;
                        }
                        if(4 == card.qualityId && !ctrl.filter.raritySelector.legendary) {
                            return false;
                        }
                        return true;
                    }

                };

                ctrl.chart = {
                    MANA_DISTRIBUTION: 0,
                    MANA_DISTRIBUTION_BY_TYPE: 1,

                    type: 1,

                    options: {
                        maintainAspectRatio: false,
                        responsive: false
                    },

                    labels: ['0', '1', '2', '3', '4', '5', '6', '7+'],
                    series: ['Cost'],
                    data: [
                        [0, 0, 0, 0, 0, 0, 0, 0]
                    ],

                    update: function() {
                        switch(ctrl.chart.type) {
                            case ctrl.chart.MANA_DISTRIBUTION:
                                ctrl.chart.applyManaDistribution();
                                break;
                            case ctrl.chart.MANA_DISTRIBUTION_BY_TYPE:
                                ctrl.chart.applyManaDistributionByType();
                                break;
                        }
                    },

                    applyManaDistribution: function() {
                        var distribution = [0, 0, 0, 0, 0, 0, 0, 0];
                        for(var id in ctrl.deck.first) {
                            if(ctrl.deck.first.hasOwnProperty(id)) {
                                var card = ctrl.deck.first[id];
                                if(card.cost > 6) {
                                    distribution[7] += card.id in ctrl.deck.second ? 2 : 1;
                                } else {
                                    distribution[card.cost] += card.id in ctrl.deck.second ? 2 : 1;
                                }
                            }
                        }
                        this.data = [distribution];
                        this.series = ['Cost'];
                        this.labels = ['0', '1', '2', '3', '4', '5', '6', '7+'];
                    },

                    applyManaDistributionByType: function() {
                        //var subDistribution = [0, 0, 0, 0, 0, 0, 0, 0];
                        var distribution = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0]];
                        for(var id in ctrl.deck.first) {
                            if(ctrl.deck.first.hasOwnProperty(id)) {
                                var card = ctrl.deck.first[id];
                                if(card.cost > 6) {
                                    switch(card.type) {
                                        case 'Minion':
                                            distribution[0][7] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                        case 'Spell':
                                            distribution[1][7] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                        case 'Weapon':
                                            distribution[2][7] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                    }
                                } else {
                                    switch(card.type) {
                                        case 'Minion':
                                            distribution[0][card.cost] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                        case 'Spell':
                                            distribution[1][card.cost] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                        case 'Weapon':
                                            distribution[2][card.cost] += card.id in ctrl.deck.second ? 2 : 1;
                                            break;
                                    }
                                }
                            }
                        }
                        this.data = distribution;
                        this.series = ['Minion', 'Spell', 'Weapon'];
                        this.labels = ['0', '1', '2', '3', '4', '5', '6', '7+'];
                    }
                };

                ctrl.deck = {
                    loaded: false,

                    first: {},
                    second: {},

                    addCard: function(id) {
                        if(!ctrl.deck.loaded) {
                            return;
                        }
                        if(!ctrl.deck.isAllowed(ctrl.getCard(id))) {
                            return;
                        }
                        if(!(id in this.second) && !(id in this.first && ctrl.getCard(id).qualityId == 4)) {
                            if (id in this.first) {
                                this.second[id] = ctrl.getCard(id);
                            } else {
                                this.first[id] = ctrl.getCard(id);
                            }
                            ctrl.saveDeck();
                            ctrl.chart.update();
                        }
                    },

                    removeCard: function(id) {
                        if(!ctrl.deck.loaded) {
                            return;
                        }
                        if(id in this.second) {
                            delete this.second[id];
                        } else if(id in this.first) {
                            delete this.first[id];
                        }
                        ctrl.saveDeck();
                        ctrl.chart.update();
                    },

                    getAmount: function(id) {
                        return id in this.second ? 2 : 1;
                    },

                    getClass: function() {
                        for(var id in this.first) {
                            if(this.first.hasOwnProperty(id)) {
                                var card = this.first[id];
                                if(card.classId != 0) {
                                    return card.classId;
                                }
                            }
                        }
                        return 0;
                    },

                    getSize: function() {
                        return Object.keys(ctrl.deck.first).length + Object.keys(ctrl.deck.second).length
                    },

                    isAllowed: function(card) {
                        var deckClass = ctrl.deck.getClass();
                        if(0 == deckClass || 0 == card.classId) {
                            return true;
                        }
                        return deckClass == card.classId;
                    }
                };

                ctrl.cards = {};

                $http.get('/deck-builder/get-cards')
                    .success(function(cards){
                        for(var i = 0; i < cards.length; i++) {
                            ctrl.cards[cards[i].id] = cards[i];
                        }
                    });

                ctrl.getCard = function(id) {
                    return ctrl.cards[id];
                };

                ctrl.getFilteredCards = function() {
                    return ctrl.filter.filter(ctrl.cards);
                };

                ctrl.saveDeck = function() {
                    $http.post('/deck-builder/' + ctrl.deckId + '/save-deck', ctrl.deck)
                        .then(function() {

                        }, function() {

                        });
                };

                angular.element(document).ready(function () {
                    $http.get('/deck-builder/' + ctrl.deckId + '/load-deck')
                        .success(function(deck){
                            for(var i = 0; i < deck.first.length; i++) {
                                ctrl.deck.first[deck.first[i]] = ctrl.getCard(deck.first[i]);
                            }
                            for(i = 0; i < deck.second.length; i++) {
                                ctrl.deck.second[deck.second[i]] = ctrl.getCard(deck.second[i]);
                            }
                            ctrl.deck.loaded = true;
                            ctrl.chart.update();
                        });
                });
            }
        };
    })
    .filter('sanitize', ['$sce', function($sce) {
        return function(htmlCode){
            return $sce.trustAsHtml(htmlCode);
        }
    }])
    .filter('class', function() {
        return function(classId) {
            switch(classId) {
                case 0: return 'Neutral';
                case 1: return 'Druid';
                case 2: return 'Hunter';
                case 3: return 'Mage';
                case 4: return 'Paladin';
                case 5: return 'Priest';
                case 6: return 'Rogue';
                case 7: return 'Shaman';
                case 8: return 'Warlock';
                case 9: return 'Warrior';
            }
        }
    })
;
