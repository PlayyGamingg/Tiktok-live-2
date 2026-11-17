package com.example.tiktoklive

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.app.Activity

class MainActivity : Activity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        val prefs = getSharedPreferences("cfg", MODE_PRIVATE)
        val pad = 48
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(pad * 4, pad, pad * 4, pad)
            setBackgroundColor(0xFF111111.toInt())
        }
        val title = TextView(this).apply {
            text = "TikTok LIVE cho Android TV"
            textSize = 30f; setTextColor(0xFFFFFFFF.toInt())
        }
        val input = EditText(this).apply {
            hint = "Nhập tên TikTok (vd: tenkenh, không cần @)"
            setText(prefs.getString("user", ""))
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI
            imeOptions = EditorInfo.IME_ACTION_DONE
            setTextColor(0xFFFFFFFF.toInt()); setHintTextColor(0xFF888888.toInt())
            textSize = 22f
        }
        val sw = Switch(this).apply {
            text = "Hiện phần bình luận"
            textSize = 22f; setTextColor(0xFFFFFFFF.toInt())
            isChecked = prefs.getBoolean("comments", true)
        }
        val tip = TextView(this).apply {
            text = "Khi đang xem: ←/→ bật/tắt chat, ↑/↓ đổi chất lượng, OK hoặc MENU mở thanh điều khiển."
            textSize = 16f; setTextColor(0xFFAAAAAA.toInt())
        }
        val btn = Button(this).apply {
            text = "Xem LIVE"; textSize = 22f
            setOnClickListener {
                val u = input.text.toString().trim().removePrefix("@")
                if (u.isEmpty()) { toast("Hãy nhập tên kênh"); return@setOnClickListener }
                prefs.edit().putString("user", u).putBoolean("comments", sw.isChecked).apply()
                startActivity(Intent(this@MainActivity, PlayerActivity::class.java).putExtra("user", u))
            }
        }
        listOf(title, input, sw, btn, tip).forEach { root.addView(it, LinearLayout.LayoutParams(-1, -2).apply { topMargin = 24 }) }
        setContentView(root)
    }
    private fun toast(s: String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}
