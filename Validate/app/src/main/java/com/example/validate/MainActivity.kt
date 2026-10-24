package com.example.validate

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.validate.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Regex dùng cho validation
    private val nameRegex = Regex("^[\\p{L}]+(\\s[\\p{L}]+)+$")           // >= 2 từ, chỉ chữ cái
    private val phoneRegex = Regex("^(0|\\+84)(3|5|7|8|9)\\d{8}$")         // SĐT Việt Nam
    private val studentIdRegex = Regex("^[A-Za-z0-9]{8,12}$")              // MSV: chữ + số, 8-12 ký tự
    private val classRegex = Regex("^[A-Za-z0-9._\\- ]{2,20}$")            // Lớp: D21CQCN01-B ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener { onRegisterClick(it) }
        binding.btnGoDetail.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }

    private fun onRegisterClick(view: View) = with(binding) {
        val name = tilName.text()
        val email = tilEmail.text()
        val phone = tilPhone.text()
        val studentId = tilStudentId.text()
        val className = tilClass.text()
        val results = listOf(
            setError(tilName, validateName(name)),
            setError(tilEmail, validateEmail(email)),
            setError(tilPhone, validatePhone(phone)),
            setError(tilStudentId, validateStudentId(studentId)),
            setError(tilClass, validateClass(className))
        )

        if (results.all { it }) {
            Snackbar.make(view, "Đăng kí thành công!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Xem chi tiết") {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_NAME, name)
                        putExtra(DetailActivity.EXTRA_EMAIL, email)
                        putExtra(DetailActivity.EXTRA_PHONE, phone)
                        putExtra(DetailActivity.EXTRA_STUDENT_ID, studentId)
                        putExtra(DetailActivity.EXTRA_CLASS, className)
                    }
                    startActivity(intent)
                }
                .show()
        } else {
            Snackbar.make(view, "Vui lòng kiểm tra lại thông tin!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun validateName(v: String): String? = when {
        v.isEmpty() -> "Họ tên không được để trống"
        !nameRegex.matches(v) -> "Họ tên phải gồm ít nhất 2 từ và chỉ chứa chữ cái"
        else -> null
    }

    private fun validateEmail(v: String): String? = when {
        v.isEmpty() -> "Email không được để trống"
        !Patterns.EMAIL_ADDRESS.matcher(v).matches() -> "Email không đúng định dạng"
        else -> null
    }

    private fun validatePhone(v: String): String? = when {
        v.isEmpty() -> "Số điện thoại không được để trống"
        !phoneRegex.matches(v) -> "SĐT không hợp lệ (VD: 0912345678 hoặc +84912345678)"
        else -> null
    }

    private fun validateStudentId(v: String): String? = when {
        v.isEmpty() -> "Mã sinh viên không được để trống"
        !studentIdRegex.matches(v) -> "MSV gồm 8-12 ký tự chữ/số (VD: B21DCCN001)"
        else -> null
    }

    private fun validateClass(v: String): String? = when {
        v.isEmpty() -> "Lớp không được để trống"
        !classRegex.matches(v) -> "Lớp không hợp lệ (VD: D21CQCN01-B)"
        else -> null
    }

    /** Gán lỗi cho TextInputLayout, trả về true nếu hợp lệ. */
    private fun setError(til: TextInputLayout, error: String?): Boolean {
        til.error = error
        return error == null
    }

    private fun TextInputLayout.text(): String = editText?.text?.toString()?.trim().orEmpty()
}