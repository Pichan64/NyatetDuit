package id.ac.amikom.nyatetduit

import java.util.*

class CRUDModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = "",
    var date: String = ""
){
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}