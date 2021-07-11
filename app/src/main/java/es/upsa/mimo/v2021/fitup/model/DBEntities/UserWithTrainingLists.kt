package es.upsa.mimo.v2021.fitup.model.DBEntities;

import androidx.room.Embedded
import androidx.room.Relation

data class UserItemWithTrainingLists(@Embedded val user: UserItem,
                                     @Relation(
                                          parentColumn = "id",
                                          entityColumn = "userId"
                                      )
                                      val trainingLists: List<TrainingListItem>
)