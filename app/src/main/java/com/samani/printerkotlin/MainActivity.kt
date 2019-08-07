package com.samani.printerkotlin

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.hardware.usb.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import java.text.Normalizer
import java.util.regex.Pattern


@Suppress("ImplicitThis")
class MainActivity : AppCompatActivity() {

    //    internal val data =
//        "{\"data\":[{\"operations\":[{\"device\":\"BV9500S320007920\",\"deviceName\":\"111 BV9500 15\",\"folio\":\"111 BV9500 15-94\",\"id\":\"d4e0359878469f435223a02e9b86935\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"reference\":\"\",\"status\":\"REGISTERED\",\"timestamp\":1565030963900,\"transactionId\":\"7d59387cd57f73763fee6f2cde8476\",\"type\":\"ADD_PRODUCT\"}],\"products\":[{\"category\":\"9cf30b16-50db-4f77-b0b7-5a37c0c710f3\",\"categoryName\":\"Cocteles\",\"commandStatus\":\"REQUESTED\",\"hash\":\"47815ab0cdb895ee52e493b86d9c53ac\",\"hash_ref\":\"\",\"id\":\"9ccf2246-2d01-478b-b370-32feb473b2a7\",\"isCustom\":false,\"name\":\"Mojito\",\"operationId\":\"d4e0359878469f435223a02e9b86935\",\"quantity\":1,\"timestamp\":1565030963902,\"transactionId\":\"7d59387cd57f73763fee6f2cde8476\",\"unit_price\":130}],\"transaction\":{\"id\":\"7d59387cd57f73763fee6f2cde8476\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"status\":\"REGISTERED\",\"timestamp\":1565030963894,\"wasPaid\":true}}],\"device\":\"BV9500S320007920\"}"
    internal val data =
        "{\"data\":[{\"operations\":[{\"device\":\"BV9500S320002805\",\"deviceName\":\"110 BV9500 14\",\"folio\":\"110 BV9500 142\",\"id\":\"3a2c21aa1caffc5d62b2931ef880b044\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"reference\":\"\",\"status\":\"COMPLETE\",\"timestamp\":1564676229322,\"transactionId\":\"f59a8fbbc61ea62497cc4fc3cbb5507c\",\"type\":\"ADD_PRODUCT\"}],\"products\":[{\"category\":\"c981fa90-a911-41d4-b7c7-ca938bba7938\",\"categoryName\":\"CaÑciño\",\"commandStatus\":\"REQUESTED\",\"hash\":\"ae579198999789952bcef60fd9cb920\",\"hash_ref\":\"\",\"id\":\"a907eafd-294e-4e19-9d48-71ab76ff13b2\",\"isCustom\":false,\"name\":\"PAPAS GAJO A LA TRUFA 300 gr\",\"operationId\":\"3a2c21aa1caffc5d62b2931ef880b044\",\"quantity\":1,\"timestamp\":1564676229331,\"transactionId\":\"f59a8fbbc61ea62497cc4fc3cbb5507c\",\"unit_price\":90},{\"category\":\"c981fa90-a911-41d4-b7c7-ca938bba7938\",\"categoryName\":\"Cancino\",\"commandStatus\":\"REQUESTED\",\"hash\":\"d56d21c57ab5f8d1c967e512ba72cf62\",\"hash_ref\":\"\",\"id\":\"fb0ae7c6-38df-465e-b813-00912235b6e6\",\"isCustom\":false,\"name\":\"CROQUETAS DE QUINOA Y CHÍA - VEGANA 220 gr\",\"operationId\":\"3a2c21aa1caffc5d62b2931ef880b044\",\"quantity\":2,\"timestamp\":1564676229342,\"transactionId\":\"f59a8fbbc61ea62497cc4fc3cbb5507c\",\"unit_price\":100},{\"category\":\"c981fa90-a911-41d4-b7c7-ca938bba7938\",\"categoryName\":\"Cancino\",\"commandStatus\":\"REQUESTED\",\"hash\":\"cb79d36b17dac3f8c38fcf265cd21\",\"hash_ref\":\"\",\"id\":\"7b8b20f6-ee41-42df-985a-935ebd59f0bb\",\"isCustom\":false,\"name\":\"PEPPERONI\",\"operationId\":\"3a2c21aa1caffc5d62b2931ef880b044\",\"quantity\":1,\"timestamp\":1564676229352,\"transactionId\":\"f59a8fbbc61ea62497cc4fc3cbb5507c\",\"unit_price\":130}],\"transaction\":{\"id\":\"f59a8fbbc61ea62497cc4fc3cbb5507c\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"status\":\"REGISTERED\",\"timestamp\":1564676229320,\"wasPaid\":true}},{\"operations\":[{\"device\":\"BV9500S320002805\",\"deviceName\":\"110 BV9500 14\",\"folio\":\"110 BV9500 144\",\"id\":\"665e3e72e46a67a15beed0f3365993b\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"reference\":\"\",\"status\":\"COMPLETE\",\"timestamp\":1564676259994,\"transactionId\":\"f82ed8b7e662839debfbebcfcc3134b9\",\"type\":\"ADD_PRODUCT\"}],\"products\":[{\"category\":\"9cf30b16-50db-4f77-b0b7-5a37c0c710f3\",\"categoryName\":\"Cocteles\",\"commandStatus\":\"REQUESTED\",\"hash\":\"ede3bf3a7d826045f277a8e3a8d83a7\",\"hash_ref\":\"\",\"id\":\"242e1605-d056-4621-91b6-07953c43b824\",\"isCustom\":false,\"name\":\"Cosmo & Roses\",\"operationId\":\"665e3e72e46a67a15beed0f3365993b\",\"quantity\":2,\"timestamp\":1564676260007,\"transactionId\":\"f82ed8b7e662839debfbebcfcc3134b9\",\"unit_price\":120},{\"category\":\"9cf30b16-50db-4f77-b0b7-5a37c0c710f3\",\"categoryName\":\"Cocteles\",\"commandStatus\":\"REQUESTED\",\"hash\":\"c216f1ac4e56ead7851826d1d8121f\",\"hash_ref\":\"\",\"id\":\"c3c4f98d-0091-4cdb-b670-1a3c8fed0a99\",\"isCustom\":false,\"name\":\"Unión Roxy\",\"operationId\":\"665e3e72e46a67a15beed0f3365993b\",\"quantity\":3,\"timestamp\":1564676260021,\"transactionId\":\"f82ed8b7e662839debfbebcfcc3134b9\",\"unit_price\":135},{\"category\":\"054af3fd-f90a-47e8-ad1d-05551f7cc5bf\",\"categoryName\":\"Botellas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"c4c723bfe3360c8dd3e67398a99b026\",\"hash_ref\":\"\",\"id\":\"8802270f-7f62-424f-8445-641bd031260d\",\"isCustom\":false,\"name\":\"Buchanan's 12\",\"operationId\":\"665e3e72e46a67a15beed0f3365993b\",\"quantity\":1,\"timestamp\":1564676260034,\"transactionId\":\"f82ed8b7e662839debfbebcfcc3134b9\",\"unit_price\":2100}],\"transaction\":{\"id\":\"f82ed8b7e662839debfbebcfcc3134b9\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"status\":\"REGISTERED\",\"timestamp\":1564676259992,\"wasPaid\":true}},{\"operations\":[{\"device\":\"BV9500S320002805\",\"deviceName\":\"110 BV9500 14\",\"folio\":\"110 BV9500 146\",\"id\":\"cfcb1ec954812f41b7fc19121b9e9c\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"reference\":\"\",\"status\":\"COMPLETE\",\"timestamp\":1564676294168,\"transactionId\":\"fc859043ed349dce9df205813516fd0\",\"type\":\"ADD_PRODUCT\"}],\"products\":[{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"c3f5315a6c96ba9e35a7bbb030a1789\",\"hash_ref\":\"\",\"id\":\"3d74d97f-caa0-48be-9d87-76585207aba2\",\"isCustom\":false,\"name\":\"Capitan Morgan Bco\",\"operationId\":\"cfcb1ec954812f41b7fc19121b9e9c\",\"quantity\":4,\"timestamp\":1564676294183,\"transactionId\":\"fc859043ed349dce9df205813516fd0\",\"unit_price\":100},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"d0948019bd551efbac828675fcf8ca\",\"hash_ref\":\"\",\"id\":\"f3291979-fd52-4c11-a094-1787491eb6f4\",\"isCustom\":false,\"name\":\"Tanqueray London\",\"operationId\":\"cfcb1ec954812f41b7fc19121b9e9c\",\"quantity\":2,\"timestamp\":1564676294197,\"transactionId\":\"fc859043ed349dce9df205813516fd0\",\"unit_price\":120},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"645b7631f96d5b54db6b93ca1b27c\",\"hash_ref\":\"\",\"id\":\"2e6b0d79-1db4-4630-af13-d4ebbb9011fd\",\"isCustom\":false,\"name\":\"Zacapa 23\",\"operationId\":\"cfcb1ec954812f41b7fc19121b9e9c\",\"quantity\":1,\"timestamp\":1564676294211,\"transactionId\":\"fc859043ed349dce9df205813516fd0\",\"unit_price\":180}],\"transaction\":{\"id\":\"fc859043ed349dce9df205813516fd0\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"status\":\"REGISTERED\",\"timestamp\":1564676294166,\"wasPaid\":true}},{\"operations\":[{\"device\":\"BV9500S320002805\",\"deviceName\":\"110 BV9500 14\",\"folio\":\"110 BV9500 148\",\"id\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"reference\":\"\",\"status\":\"COMPLETE\",\"timestamp\":1564676346515,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"type\":\"ADD_PRODUCT\"}],\"products\":[{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"b66495466044f8cb55eb94a8daa174ef\",\"hash_ref\":\"\",\"id\":\"db745976-19bd-419b-9af4-0f36338805b1\",\"isCustom\":false,\"name\":\"Smirnoff\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":5,\"timestamp\":1564676346517,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":100},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"e7bf5b3694ab769ddb73dd95038bd33\",\"hash_ref\":\"\",\"id\":\"3d74d97f-caa0-48be-9d87-76585207aba2\",\"isCustom\":false,\"name\":\"Capitan Morgan Bco\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":7,\"timestamp\":1564676346519,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":100},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"b5ca33b44b29e258542d941fbd8945\",\"hash_ref\":\"\",\"id\":\"c801ad71-aaeb-4c52-842d-a5347fa9a90a\",\"isCustom\":false,\"name\":\"Unión Joven\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":2,\"timestamp\":1564676346520,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":110},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"a846728450d2ef18ba5db92d9ede4d\",\"hash_ref\":\"\",\"id\":\"7d1a6c3a-da06-4244-92fd-2249becc6081\",\"isCustom\":false,\"name\":\"Don Julio BCO\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":6,\"timestamp\":1564676346522,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":110},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"671af91d8875ff6bd97ee581ec16dea\",\"hash_ref\":\"\",\"id\":\"f3291979-fd52-4c11-a094-1787491eb6f4\",\"isCustom\":false,\"name\":\"Tanqueray London\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":4,\"timestamp\":1564676346523,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":120},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"8b3138503cb9ea60e325cf178510ce\",\"hash_ref\":\"\",\"id\":\"898dd968-a0d5-4640-a5bd-4e5c9970f4f0\",\"isCustom\":false,\"name\":\"Unión El Viejo\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":3,\"timestamp\":1564676346524,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":140},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"1450ad39f9254de70bf0efe8c3b7f6\",\"hash_ref\":\"\",\"id\":\"6c5f8fb8-6d22-40f2-9d9e-aa233a89e0a8\",\"isCustom\":false,\"name\":\"Buchanan's 12\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":8,\"timestamp\":1564676346526,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":150},{\"category\":\"0b8d2ceb-39e3-498b-be3d-ea71f37abb20\",\"categoryName\":\"Copas\",\"commandStatus\":\"REQUESTED\",\"hash\":\"dff57694411697b28fb66f206753c261\",\"hash_ref\":\"\",\"id\":\"2e6b0d79-1db4-4630-af13-d4ebbb9011fd\",\"isCustom\":false,\"name\":\"Zacapa 23\",\"operationId\":\"9fc9e6943b5eff2162a6b891a6f5174\",\"quantity\":1,\"timestamp\":1564676346527,\"transactionId\":\"c47061d1af766c18c96834441d5bd580\",\"unit_price\":180}],\"transaction\":{\"id\":\"c47061d1af766c18c96834441d5bd580\",\"placeId\":\"b048703d-a99f-475f-a15f-1134bfb9307d\",\"status\":\"REGISTERED\",\"timestamp\":1564676346514,\"wasPaid\":true}}],\"device\":\"BV9500S320002805\"}"
    internal val dataJson: JSONObject = JSONObject(data)

    private var mUsbManager: UsbManager? = null
    private var mDevice: UsbDevice? = null
    private var mConnection: UsbDeviceConnection? = null
    private var mInterface: UsbInterface? = null
    private var mEndPoint: UsbEndpoint? = null
    private var mPermissionIntent: PendingIntent? = null
    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    private val forceCLaim = true

    internal lateinit var mDeviceList: HashMap<String, UsbDevice>
    internal lateinit var mDeviceIterator: Iterator<UsbDevice>
    internal lateinit var testBytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val print = print

        mUsbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        mDeviceList = mUsbManager!!.getDeviceList()

        if (mDeviceList.size > 0) {
            mDeviceIterator = mDeviceList.values.iterator()

            Toast.makeText(this, "Device List Size: " + mDeviceList.size.toString(), Toast.LENGTH_SHORT).show()

            val textView = usbDevice
            var usbDevice = ""
            while (mDeviceIterator.hasNext()) {
                val usbDevice1 = mDeviceIterator.next()
                usbDevice += "\n" +
                        "DeviceID: " + usbDevice1.deviceId + "\n" +
                        "DeviceName: " + usbDevice1.deviceName + "\n" +
                        "Protocol: " + usbDevice1.deviceProtocol + "\n" +
                        "Product Name: " + usbDevice1.productName + "\n" +
                        "Manufacturer Name: " + usbDevice1.manufacturerName + "\n" +
                        "DeviceClass: " + usbDevice1.deviceClass + " - " + translateDeviceClass(usbDevice1.deviceClass) + "\n" +
                        "DeviceSubClass: " + usbDevice1.deviceSubclass + "\n" +
                        "VendorID: " + usbDevice1.vendorId + "\n" +
                        "ProductID: " + usbDevice1.productId + "\n"

                val interfaceCount = usbDevice1.interfaceCount
                Toast.makeText(this, "INTERFACE COUNT: $interfaceCount", Toast.LENGTH_SHORT).show()

                mDevice = usbDevice1

                Toast.makeText(this, "Device is attached", Toast.LENGTH_SHORT).show()
                textView.text = usbDevice
            }

            mPermissionIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), 0)
            val filter = IntentFilter(ACTION_USB_PERMISSION)
            registerReceiver(mUsbReceiver, filter)

            mUsbManager!!.requestPermission(mDevice, mPermissionIntent)
        } else {
            Toast.makeText(this, "Please attach printer via USB", Toast.LENGTH_SHORT).show()
        }
        print.setOnClickListener { print(mConnection, mInterface) }
    }

    private fun print(connection: UsbDeviceConnection?, usbInterface: UsbInterface?) {
        val test = ed_txt.text.toString() + "\n\n"
        testBytes = test.toByteArray()

        if (usbInterface == null) {
            Toast.makeText(this, "INTERFACE IS NULL", Toast.LENGTH_SHORT).show()
        } else if (connection == null) {
            Toast.makeText(this, "CONNECTION IS NULL", Toast.LENGTH_SHORT).show()
        } else if (forceCLaim == null) {
            Toast.makeText(this, "FORCE CLAIM IS NULL", Toast.LENGTH_SHORT).show()
        } else {

            connection.claimInterface(usbInterface, forceCLaim)

            //            String content = "115";
            //
            //            byte[] contents = content.getBytes();
            //            byte[] formats = {(byte) 0x1d, (byte) 0x6b, (byte) 0x49, (byte) contents.length};
            //
            //            final byte[] bytes = new byte[formats.length + contents.length];
            //
            //            System.arraycopy(formats, 0, bytes, 0, formats.length);
            //            System.arraycopy(contents, 0, bytes, formats.length, contents.length);
            //
            //
            //            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
            //                    R.drawable.test);
            //            final byte[] command = Utils.decodeBitmap(bmp);


            val thread = Thread(object : Runnable {


                private val dateTime: Array<String>
                    get() {
                        val c = Calendar.getInstance()
                        val dateTime = Array<String>(2) { index -> "" }
                        dateTime[0] = zeroPadding(c.get(Calendar.DAY_OF_MONTH), 2) + "/" + zeroPadding(
                            c.get(Calendar.MONTH) + 1,
                            2
                        ) + "/" + c.get(Calendar.YEAR)
                        dateTime[1] = zeroPadding(c.get(Calendar.HOUR_OF_DAY), 2) + ":" + zeroPadding(
                            c.get(Calendar.MINUTE),
                            2
                        ) + ":" + zeroPadding(c.get(Calendar.SECOND), 2)
                        return dateTime
                    }

                override fun run() {

                    val device = dataJson.getString("device")
                    printTicket("Copia Mesero")
                    printTicket("Copia Barras")

                }

                fun printTicket(label: String) {
                    printCustom("FESTIVAL CABULAND", 1, 1)
                    printPhoto(R.drawable.test)
                    printCustom(label, 0, 1)
                    val dateTime = dateTime[0] + " " + dateTime[1] + " hrs"
                    printCustom(dateTime, 1, 1)
                    printRow("PRODUCTO..........", "CATEGORIA", "CANTIDAD")
                    val data = dataJson.getJSONArray("data")
                    for (i in 0..(data.length() - 1)) {
                        val transactionData = data.getJSONObject(i)
                        val operations = transactionData.getJSONArray("operations")
                        val products = transactionData.getJSONArray("products")
                        val transaction = transactionData.getJSONObject("transaction")

                        for (j in 0..(operations.length() - 1)) {
                            val operationData = operations.getJSONObject(j)
                            printNewLine()
                            printCustom("Folio: " + operationData.getString("folio"), 0, 1)
                            for (k in 0..(products.length() - 1)) {
                                val productData = products.getJSONObject(k)
                                if (productData.getString("operationId") == operationData.getString("id")) {
                                    printRow(
                                        productData.getString("name"),
                                        productData.getString("categoryName"),
                                        productData.getInt("quantity").toString()
                                    )
                                }
                            }
                        }
                    }
                    printNewLine()
                    printCustom("-".repeat(32), 0, 1)
                    printNewLine()
                }

                fun unAccent(s: String): String {
                    val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
                    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                    return pattern.matcher(temp).replaceAll("")
                }

                fun zeroPadding(value: Int, size: Int): String {
                    val aux = "0".repeat(size) + value.toString()
                    return aux.substring(aux.length - size)
                }

                fun printRow(col1: String, col2: String, col3: String) {
                    val row = paddString(unAccent(col1).trim(), 18) + " " + paddString(
                        unAccent(col2).trim(),
                        9
                    ) + " " + paddString(col3, 3)
                    printText(row)
                    printNewLine()
                }

                fun paddString(value: String, n: Int): String {
                    return (value + " ".repeat(n)).substring(0, n)
                }

                fun printCommand(data: ByteArray) {
                    connection.bulkTransfer(mEndPoint, data, data.size, 0)
                }

                //print custom
                private fun printCustom(msg: String, size: Int, align: Int) {
                    //Print config "mode"
                    val cc = byteArrayOf(0x1B, 0x21, 0x03)  // 0- normal size text
                    //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
                    val bb = byteArrayOf(0x1B, 0x21, 0x08)  // 1- only bold text
                    val bb2 = byteArrayOf(0x1B, 0x21, 0x20) // 2- bold with medium text
                    val bb3 = byteArrayOf(0x1B, 0x21, 0x10) // 3- bold with large text
                    when (size) {
                        0 -> printCommand(cc)
                        1 -> printCommand(bb)
                        2 -> printCommand(bb2)
                        3 -> printCommand(bb3)
                    }

                    when (align) {
                        0 ->
                            //left align
                            printCommand(PrinterCommands.ESC_ALIGN_LEFT)
                        1 ->
                            //center align
                            printCommand(PrinterCommands.ESC_ALIGN_CENTER)
                        2 ->
                            //right align
                            printCommand(PrinterCommands.ESC_ALIGN_RIGHT)
                    }
                    printCommand(msg.toByteArray())
                    val aux = byteArrayOf(PrinterCommands.LF)
                    printCommand(aux)
                    //printCommand(cc);
                    //printNewLine();

                }

                //print photo
                fun printPhoto(img: Int) {
                    try {
                        val bmp = BitmapFactory.decodeResource(
                            resources,
                            img
                        )
                        if (bmp != null) {
                            val command = Utils.decodeBitmap(bmp)
                            printCommand(PrinterCommands.ESC_ALIGN_CENTER)
                            printText(command ?: ByteArray(0))
                        } else {
                            Log.e("Print Photo error", "the file isn't exists")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("PrintTools", "the file isn't exists")
                    }

                }

                //print unicode
                fun printUnicode() {
                    printCommand(PrinterCommands.ESC_ALIGN_CENTER)
                    printText(Utils.UNICODE_TEXT)
                }


                //print new line
                private fun printNewLine() {
                    printCommand(PrinterCommands.FEED_LINE)
                }

                fun resetPrint() {
                    printCommand(PrinterCommands.ESC_FONT_COLOR_DEFAULT)
                    printCommand(PrinterCommands.FS_FONT_ALIGN)
                    printCommand(PrinterCommands.ESC_ALIGN_LEFT)
                    printCommand(PrinterCommands.ESC_CANCEL_BOLD)
                    val aux = byteArrayOf(PrinterCommands.LF)
                    printCommand(aux)
                }

                //print text
                private fun printText(msg: String) {
                    printCommand(msg.toByteArray())

                }

                //print byte[]
                private fun printText(msg: ByteArray) {
                    printCommand(msg)
                    printNewLine()
                }


                private fun leftRightAlign(str1: String, str2: String): String {
                    var ans = str1 + str2
                    if (ans.length < 31) {
                        val n = 31 - str1.length + str2.length
                        ans = str1 + String(CharArray(n)).replace("\u0000", " ") + str2
                    }
                    return ans
                }
            })
            thread.run()
        }
    }

    internal val mUsbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (ACTION_USB_PERMISSION == action) {
                synchronized(this) {
                    val device = intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            //                            mInterface = device.getInterface(0);
                            //                            mEndPoint = mInterface.getEndpoint(1);// 0 IN and  1 OUT to printer.
                            //                            mConnection = mUsbManager.openDevice(device);

                            mInterface = device.getInterface(0)
                            mEndPoint = mInterface!!.getEndpoint(0)
                            mConnection = mUsbManager!!.openDevice(device)

                        }
                    } else {
                        Toast.makeText(context, "PERMISSION DENIED FOR THIS DEVICE", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun translateDeviceClass(deviceClass: Int): String {

        when (deviceClass) {

            UsbConstants.USB_CLASS_APP_SPEC -> return "Application specific USB class"

            UsbConstants.USB_CLASS_AUDIO -> return "USB class for audio devices"

            UsbConstants.USB_CLASS_CDC_DATA -> return "USB class for CDC devices (communications device class)"

            UsbConstants.USB_CLASS_COMM -> return "USB class for communication devices"

            UsbConstants.USB_CLASS_CONTENT_SEC -> return "USB class for content security devices"

            UsbConstants.USB_CLASS_CSCID -> return "USB class for content smart card devices"

            UsbConstants.USB_CLASS_HID -> return "USB class for human interface devices (for example, mice and keyboards)"

            UsbConstants.USB_CLASS_HUB -> return "USB class for USB hubs"

            UsbConstants.USB_CLASS_MASS_STORAGE -> return "USB class for mass storage devices"

            UsbConstants.USB_CLASS_MISC -> return "USB class for wireless miscellaneous devices"

            UsbConstants.USB_CLASS_PER_INTERFACE -> return "USB class indicating that the class is determined on a per-interface basis"

            UsbConstants.USB_CLASS_PHYSICA -> return "USB class for physical devices"

            UsbConstants.USB_CLASS_PRINTER -> return "USB class for printers"

            UsbConstants.USB_CLASS_STILL_IMAGE -> return "USB class for still image devices (digital cameras)"

            UsbConstants.USB_CLASS_VENDOR_SPEC -> return "Vendor specific USB class"

            UsbConstants.USB_CLASS_VIDEO -> return "USB class for video devices"

            UsbConstants.USB_CLASS_WIRELESS_CONTROLLER -> return "USB class for wireless controller devices"

            else -> return "Unknown USB class!"
        }
    }
}
