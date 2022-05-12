package br.edu.ifsp.scl.ads.pdm.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.pdm.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico:Random

    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadoBt.setOnClickListener {
            val resultado:Int = geradorRandomico.nextInt(1..6)
            "A face sorteada foi $resultado".also { activityMainBinding.resultadoTv.text = it }
            val nomeImagem = "dice_$resultado"
            activityMainBinding.resultadoIv.setImageResource(
                resources.getIdentifier(nomeImagem, "mipmap", packageName)
            )


        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado->
            if (resultado.resultCode == RESULT_OK){
                //modificações na view
                    if(resultado.data != null){
                        val configuracao: Configuracao? = resultado.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)

                        if (configuracao != null) {

                            if (configuracao.numeroFaces > 6){
                                if (configuracao.numeroDados == 1){
                                    activityMainBinding.jogarDadoBt.setOnClickListener {
                                        val limite: Int = configuracao.numeroFaces
                                        val resultadoAlto: Int = geradorRandomico.nextInt(1..limite)
                                        "A face sorteada foi $resultadoAlto".also {
                                            activityMainBinding.resultadoTv.text = it
                                        }
                                        activityMainBinding.resultadoIv.setImageResource(0)
                                        activityMainBinding.resultado2Iv.setImageResource(0)
                                    }
                                }else if (configuracao.numeroDados == 2){
                                    activityMainBinding.jogarDadoBt.setOnClickListener {
                                        val limite: Int = configuracao.numeroFaces
                                        val resultadoAlto: Int = geradorRandomico.nextInt(1..limite)
                                        val resultadoAltoDadoDois: Int =
                                            geradorRandomico.nextInt(1..limite)
                                        "As faces sorteadas foram $resultadoAlto e $resultadoAltoDadoDois".also {
                                            activityMainBinding.resultadoTv.text = it
                                        }
                                        activityMainBinding.resultadoIv.setImageResource(0)
                                        activityMainBinding.resultado2Iv.setImageResource(0)
                                    }
                                    }else{
                                    activityMainBinding.jogarDadoBt.setOnClickListener {
                                        "Erro".also { activityMainBinding.resultadoTv.text = it }
                                        activityMainBinding.resultadoIv.setImageResource(0)
                                        activityMainBinding.resultado2Iv.setImageResource(0)
                                    }


                                }

                            }
                            else {
                                if (configuracao.numeroDados == 2) {
                                    activityMainBinding.jogarDadoBt.setOnClickListener {
                                        val limite: Int = configuracao.numeroFaces
                                        val resultado: Int = geradorRandomico.nextInt(1..limite)
                                        val nomeImagem = "dice_$resultado"
                                        activityMainBinding.resultadoIv.setImageResource(
                                            resources.getIdentifier(
                                                nomeImagem,
                                                "mipmap",
                                                packageName
                                            )
                                        )

                                        val resultado2: Int = geradorRandomico.nextInt(1..limite)
                                        "As faces sorteadas foram $resultado e $resultado2".also {
                                            activityMainBinding.resultadoTv.text = it
                                        }
                                        val nomeImagem2 = "dice_$resultado2"
                                        activityMainBinding.resultado2Iv.setImageResource(
                                            resources.getIdentifier(
                                                nomeImagem2,
                                                "mipmap",
                                                packageName
                                            )
                                        )


                                    }
                                } else if (configuracao.numeroDados == 1) {
                                    activityMainBinding.jogarDadoBt.setOnClickListener {
                                        val limite: Int = configuracao.numeroFaces
                                        val resultado: Int = geradorRandomico.nextInt(1..limite)
                                        "A face sorteada foi $resultado".also {
                                            activityMainBinding.resultadoTv.text = it
                                        }
                                        val nomeImagem = "dice_$resultado"
                                        activityMainBinding.resultadoIv.setImageResource(
                                            resources.getIdentifier(
                                                nomeImagem,
                                                "mipmap",
                                                packageName
                                            )

                                        )
                                        activityMainBinding.resultado2Iv.setImageResource(0)

                                    }
                                }
                            }
                        }



                    }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsMi){
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}