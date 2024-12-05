package DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :

    SQLiteOpenHelper(context, "Login.db" , null,1) {
    // users 테이블 생성
    override fun onCreate(mydb: SQLiteDatabase?) {
        mydb!!.execSQL("create Table users(id TEXT primary key, password TEXT, nick TEXT, phone TEXT)")
    }

    //정보 갱신
    override fun onUpgrade(mydb: SQLiteDatabase?, i: Int, i2: Int) {
        mydb!!.execSQL("drop Table if exists users")
    }

    // id, password, nick, phone 삽입 (성공시 true, 실패시 false)
    fun insertData (id: String?, password: String?, nick: String?, phone: String?) : Boolean {
        val mydb = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("password", password)
        contentValues.put("nick", nick)
        contentValues.put("phone", phone)
        val result = mydb.insert("users", null, contentValues)
        mydb.close()
        return if (result == -1L) false else true
    }

    //사용자 아이디가 없으면 false, 이미 존재하면 true
    fun checkUser(id: String?): Boolean {
        val mydb = this.readableDatabase
        var res = true
        val cursor = mydb.rawQuery("Select * from users where id=?", arrayOf(id))
        if (cursor.count <= 0) res = false
        return res
    }

    //사용자 닉네임이 없으면 false, 이미 존재하면 true
    fun checkNick(nick: String?): Boolean {
        val mydb = this.readableDatabase
        var res = true
        val cursor = mydb.rawQuery("Select * from users where nick=?", arrayOf(nick))
        if (cursor.count <= 0) res = false
        return res
    }

    //해당 id, password가 있는지 확인
    fun checkUserpass(id: String?, password: String?): Boolean {
        val mydb = this.writableDatabase
        var res = true
        val cursor = mydb.rawQuery(
            "Select * from users where id = ? and password = ? ",
            arrayOf(id,password)
        )
        if (cursor.count <= 0) res = false
        return res
    }

    //DB 이름을 Login.db 로 설정
    companion object{
        const val DBNAME = "Login.db"
    }
}