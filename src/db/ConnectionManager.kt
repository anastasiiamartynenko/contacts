package contacts.db

import java.io.File
import kotlin.io.readText
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import contacts.entities.Contact
import contacts.entities.EntityBase
import contacts.entities.Organization

class ConnectionManager(private val filename: String?) {
    private var file: File? = null
    var isConnected: Boolean = false

    val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(EntityBase::class.java, "type")
                .withSubtype(Contact::class.java, "contact")
                .withSubtype(Organization::class.java, "organization")
        )
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(MutableList::class.java, EntityBase::class.java)
    val jsonAdapter = moshi.adapter<MutableList<EntityBase>>(type)

    fun load(): MutableList<EntityBase> {
        if (file != null) {
            val json = file!!.readText(Charsets.UTF_8)

            return if (json.isNotEmpty()) jsonAdapter.fromJson(json)?.toMutableList()
                ?: mutableListOf<EntityBase>()
            else mutableListOf<EntityBase>()
        } else {
            return mutableListOf<EntityBase>()
        }
    }

    fun persist(data: MutableList<EntityBase>) {
        val jsonified = jsonAdapter.toJson(data)
        file?.writeText(jsonified)
    }

    fun closeConnection() {
        if (file != null && file?.exists() == true) {
            file?.delete()
        }
    }

    init {
        if (filename != null) {
            file = File(filename)

            if (!file!!.exists()) {
                file!!.createNewFile()
            }
            isConnected = true
        }
    }
}