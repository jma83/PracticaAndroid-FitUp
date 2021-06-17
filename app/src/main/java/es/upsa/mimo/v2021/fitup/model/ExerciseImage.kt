package es.upsa.mimo.v2021.fitup.model

data class ExerciseImage(val id: Int, val uuid: String, val exercise_base: Int,
                    val image: String, val is_main: Boolean, val status: Int)