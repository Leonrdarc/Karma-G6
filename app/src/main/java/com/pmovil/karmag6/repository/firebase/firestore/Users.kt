import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pmovil.karmag6.model.User
import kotlinx.coroutines.tasks.await

class UsersRepository {
    private var db: FirebaseFirestore = Firebase.firestore

    suspend fun create(user: User): DocumentReference {
        return db.collection("users").add(user).await()
    }

    suspend fun findOneByEmail(email: String): User {
        return db.collection("users").whereEqualTo("email", email).get().await().toObjects(User::class.java)[0]
    }

    suspend fun setKarma (email: String, newKarma: Int){
        return db.collection("users")
            .whereEqualTo("email",email)
            .limit(1).get().await().forEach{
                it.reference.update("karma", newKarma)
            }
    }
}