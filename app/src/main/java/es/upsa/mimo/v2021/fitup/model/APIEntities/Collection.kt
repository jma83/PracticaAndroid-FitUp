package es.upsa.mimo.v2021.fitup.model.APIEntities

abstract class Collection(){
    open val count: Int = 0
    open val next: String? = ""
    open val previous: String? = ""
}
