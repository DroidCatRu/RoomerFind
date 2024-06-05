package ru.roomerfind.backend.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.function
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.droidcat.roomerfind.model.network.UserInfoDTO
import ru.roomerfind.backend.database.entity.ReactionsTable
import ru.roomerfind.backend.database.entity.ReactionsTable.Reaction.DISLIKE
import ru.roomerfind.backend.database.entity.ReactionsTable.Reaction.LIKE
import ru.roomerfind.backend.database.entity.UserData
import ru.roomerfind.backend.database.entity.UserTable
import ru.roomerfind.backend.database.entity.toUserData

class UserRepository(
    private val db: Database,
) {

    fun createUser(
        email: String,
        password: String,
    ): Long = transaction(db) {
        UserTable.insertAndGetId {
            it[this.email] = email
            it[this.password] = password
        }.value
    }

    fun getUserByEmail(email: String): UserData? = transaction(db) {
        UserTable
            .selectAll()
            .where { UserTable.email eq email }
            .singleOrNull()
            ?.toUserData()
    }

    fun getAvatar(id: Long): String? = transaction(db) {
        UserTable
            .selectAll()
            .where { UserTable.id eq id }
            .singleOrNull()
            ?.get(UserTable.avatar)
    }

    fun updateAvatar(
        id: Long,
        filename: String,
    ): Boolean = transaction(db) {
        UserTable.update(
            where = { UserTable.id eq id }
        ) {
            it[avatar] = filename
        } == 1
    }

    fun getUserData(id: Long, requesterId: Long): UserData? = transaction(db) {
        UserTable
            .selectAll()
            .where { UserTable.id eq id }
            .singleOrNull()
            ?.toUserData {
                this[UserTable.id].value == requesterId ||
                    getMatches(requesterId).any { id == it.id }
            }
    }

    fun updateUserInfo(
        id: Long,
        info: UserInfoDTO,
    ): Boolean = transaction(db) {
        UserTable.update(
            where = { UserTable.id eq id }
        ) {
            it[name] = info.name
            it[age] = info.age
            it[description] = info.description
            it[contactInfo] = info.contactInfo

            it[lat] = info.preferences.lat
            it[long] = info.preferences.long
            it[zoom] = info.preferences.zoom
            it[radiusKm] = info.preferences.radius

            it[minPrice] = info.preferences.minPrice
            it[maxPrice] = info.preferences.maxPrice

            it[minAge] = info.preferences.minAge
            it[maxAge] = info.preferences.maxAge
        } == 1
    }

    fun getNextFinder(id: Long): UserData? = transaction(db) {
        val reactionFiltered = ReactionsTable
            .select(ReactionsTable.receiver)
            .where { ReactionsTable.initiator eq id }

        val a = UserTable.alias("a")
        val b = UserTable.alias("b")

        val priceAndAgeFiltered = a.join(
            b,
            JoinType.CROSS,
            a[UserTable.id],
            b[UserTable.id],
        )
            .select(b[UserTable.id])
            .where {
                (a[UserTable.id] neq b[UserTable.id]) and
                    (a[UserTable.minAge] lessEq b[UserTable.age]) and
                    (a[UserTable.maxAge] greaterEq b[UserTable.age]) and
                    (b[UserTable.minAge] lessEq a[UserTable.age]) and
                    (b[UserTable.maxAge] greaterEq a[UserTable.age]) and
                    (a[UserTable.minPrice] lessEq b[UserTable.maxPrice]) and
                    (a[UserTable.maxPrice] greaterEq b[UserTable.minPrice]) and
                    (a[UserTable.id] eq id)
            }

        val earthRadius = 6371.0
        val pi = 3.14

        val locationFiltered = a.join(
            b,
            JoinType.CROSS,
            a[UserTable.id],
            b[UserTable.id],
        )
            .select(b[UserTable.id])
            .where {
                (a[UserTable.id] neq b[UserTable.id]) and
                    (
                        (
                            (
                                (
                                    (a[UserTable.lat] * pi / 180.0).function("sin") *
                                        (b[UserTable.lat] * pi / 180.0).function("sin")
                                    ) +
                                    (a[UserTable.lat] * pi / 180.0).function("cos") *
                                    (b[UserTable.lat] * pi / 180.0).function("cos") *
                                    (
                                        (b[UserTable.long] * pi / 180.0) -
                                            (a[UserTable.long] * pi / 180.0)
                                        ).function("cos")
                                ).function("acos") * earthRadius
                            ) lessEq (a[UserTable.radiusKm] + b[UserTable.radiusKm])
                        ) and
                    (a[UserTable.id] eq id)
            }

        UserTable
            .selectAll()
            .where {
                (UserTable.id neq id) and
                    (UserTable.id notInSubQuery reactionFiltered) and
                    (UserTable.id inSubQuery priceAndAgeFiltered) and
                    (UserTable.id inSubQuery locationFiltered)
            }
            .firstOrNull()
            ?.toUserData()
    }

    fun likeLastFinder(id: Long) {
        transaction(db) {
            getNextFinder(id)?.let { finder ->
                ReactionsTable.insert {
                    it[initiator] = id
                    it[receiver] = finder.id
                    it[reaction] = LIKE
                }
            }
        }
    }

    fun dislikeLastFinder(id: Long) {
        transaction(db) {
            getNextFinder(id)?.let { finder ->
                ReactionsTable.insert {
                    it[initiator] = id
                    it[receiver] = finder.id
                    it[reaction] = DISLIKE
                }
            }
        }
    }

    fun getMatches(id: Long): List<UserData> = transaction(db) {
        val a = ReactionsTable.alias("a")
        val b = ReactionsTable.alias("b")

        val reactionsFiltered = a.join(
            b,
            JoinType.INNER,
            a[ReactionsTable.initiator],
            b[ReactionsTable.receiver]
        )
            .select(b[ReactionsTable.initiator])
            .where {
                (a[ReactionsTable.initiator] eq id) and
                    (b[ReactionsTable.initiator] eq a[ReactionsTable.receiver]) and
                    (a[ReactionsTable.reaction] eq ReactionsTable.Reaction.LIKE) and
                    (b[ReactionsTable.reaction] eq ReactionsTable.Reaction.LIKE)
            }

        UserTable
            .selectAll()
            .where { UserTable.id inSubQuery reactionsFiltered }
            .map { it.toUserData { true } }
    }
}
