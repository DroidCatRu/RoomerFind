package ru.droidcat.roomerfind.server.database.contact_info

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.droidcat.roomerfind.server.database.preferences.Preferences
import ru.droidcat.roomerfind.server.database.user_info.UserInfo
import ru.droidcat.roomerfind.server.features.user.ContactsReceiveRemote

object ContactInfo : IntIdTable() {

    val phone = ContactInfo.varchar("phone", 15)
    val email = ContactInfo.varchar("email", 100)
    val priority = ContactInfo.integer("priority")

    fun getContacts(token: String): ContactInfoDTO? {
        return try {
            transaction {
                val userInfo = UserInfo.getUserByToken(token)
                if (userInfo != null) {
                    ContactInfo.select { ContactInfo.id eq userInfo.contacts }.map {
                        ContactInfoDTO (
                            phone = it[phone],
                            email = it[email],
                            priority = it[priority]
                        )
                    }.single()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getContactById(id: Int): ContactInfoDTO? {
        return try {
            transaction {
                ContactInfo.select { ContactInfo.id eq id }.map {
                    ContactInfoDTO (
                        phone = it[phone],
                        email = it[email],
                        priority = it[priority]
                    )
                }.single()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateContacts(token: String, userContacts: ContactsReceiveRemote): Boolean {
        if ((userContacts.priority != 0) and (userContacts.priority != 1))
            return false
        return try {
            transaction {
                val userInfo = UserInfo.getUserByToken(token)
                if (userInfo?.contacts != null ) {
                    ContactInfo.update ({ ContactInfo.id eq userInfo.contacts } ) {
                        it[phone] = userContacts.phone
                        it[email] = userContacts.email
                        it[priority] = userContacts.priority
                    }
                } else {
                    val contactsRowId = ContactInfo.insertAndGetId {
                        it[phone] = userContacts.phone
                        it[email] = userContacts.email
                        it[priority] = userContacts.priority
                    }.value

                    val userId = UserInfo.getUserIdByToken(token)
                    UserInfo.update({ UserInfo.id eq userId }) {
                        it[contacts] = contactsRowId
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}