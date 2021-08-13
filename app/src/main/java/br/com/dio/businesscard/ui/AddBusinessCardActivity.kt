package br.com.dio.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import br.com.dio.businesscard.App
import br.com.dio.businesscard.R
import br.com.dio.businesscard.data.BusinessCard
import br.com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch

class AddBusinessCardActivity : AppCompatActivity() {

    private var mDefaultColor: String = ""

    private val binding by lazy {ActivityAddBusinessCardBinding.inflate(layoutInflater)}

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListener()
    }

    private fun insertListener(){
        binding.btnClose.setOnClickListener {
           // val  intent = Intent(this@AddBusinessCardActivity, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        binding.btnColor.setOnClickListener { _ ->
            MaterialColorPickerDialog
                .Builder(this)        			// Pass Activity Instance
                .setTitle("Choose Color")           	// Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   	// Default ColorShape.CIRCLE
                .setColorSwatch(ColorSwatch._300)   	// Default ColorSwatch._500
                .setDefaultColor(mDefaultColor) 		// Pass Default Color
                .setColorListener { color, colorHex ->
                    mDefaultColor = colorHex
                }
                .show()
        }

        binding.btnConfirmar.setOnClickListener{
            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                fundoPersonalizado = mDefaultColor.uppercase() ,
            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_sucess,Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}