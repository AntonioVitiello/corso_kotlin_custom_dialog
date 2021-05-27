package ant.vit.corsokotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ant.vit.corsokotlin.tools.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

/**
 * Created by Antonio Vitiello
 * Quando si utilizzano le funzioni inline
 * non viene allocato alcun oggetto anonimo implicito
 * quindi non viene allocato nessun ulteriore oggetto
 * e nessuna chiamata ad un metodo virtuale
 * in alcuni casi con notevole risparmio di memoria
 *
 * Kotlin cancella le informazioni del tipo generico in fase di esecuzione
 * ma per evitare questa limitazione possiamo usare le funzioni inline con reified
 * il compilatore potrà così reificare (prendere per concreto l’astratto)
 * le informazioni del tipo generico per le funzioni inline
 */
class CustomAlertActivity : AppCompatActivity(), ICustomAlertDialog {

    companion object {
        private const val TAG = "CustomAlertActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents(savedInstanceState)
    }

    private fun initComponents(savedInstanceState: Bundle?) {
        inlineButton.setOnClickListener {
            cleanAllMessages()
            inlineExample()
//            inlineExampleWithIndex()
            hasSavedInstanceBundle(savedInstanceState)
        }
        dialogButton.setOnClickListener { showDialog() }
    }

    private fun cleanAllMessages() {
        helloText.text = null
        Log.d(LOG_TAG, "Cleaning all messages")
    }

    private fun inlineExample() {
//        val numbers = listOf(1, 2, 3, 4, 5)
//        val numbers = List(10) { Random.nextInt(0, 100) }
        val numbers = List(10) { (0..100).random() }

        numbers.each { number ->
            addMessage("a number in list: $number")
        }
    }

    private fun inlineExampleWithIndex() {
        val numbers = List(10) { Random.nextInt(0, 100) }

        numbers.eachWithIndex { index, number ->
            addMessage("${index + 1}) number in list: $number")
        }
    }

    /**
     * An example of function with inline reified
     */
    private fun hasSavedInstanceBundle(savedInstanceState: Bundle?) {
        val isDefined = savedInstanceState?.isOfType<Bundle>() ?: false
        addMessage("has a savedInstanceState: $isDefined")
    }

    private fun addMessage(line: String) {
//        val message = helloText.text.toString().plus("$line\n")
//        helloText.text = message
        helloText += line
        helloScroll.post {
            helloScroll.fullScroll(View.FOCUS_DOWN)
        }
        Log.d(LOG_TAG, line)
    }

    private fun showDialog() {
        CustomAlertDialog.newInstance(getString(R.string.attention), getString(R.string.procede_body))
            .show(supportFragmentManager, CustomAlertDialog.TAG)
    }

    override fun isAccepted(accepted: Boolean) {
        addMessage("is accepted: $accepted ${if (accepted) "...zzzvibrate" else ""}")
    }

}
