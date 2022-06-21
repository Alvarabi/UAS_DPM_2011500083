package com.example.uasdpm2011500083

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AdapterDosen (
    private val getContext: Context,
    private val customListItem: ArrayList<DataDosen>
): ArrayAdapter<DataDosen>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNamaDosen = listLayout.findViewById(R.id.tvNamaDosen)
                tvkodeNIDN = listLayout.findViewById(R.id.tvkodeNIDN)
                tvProgramStudi = listLayout.findViewById(R.id.tvProgramStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNamaDosen!!.setText(listItem.nmDosen)
        holder.tvkodeNIDN!!.setText(listItem.NIDN)
        holder.tvProgramStudi!!.setText(listItem.ProgramStudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("NIDN", listItem.NIDN)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("jabatan", listItem.jabatan)
            i.putExtra("golongan", listItem.GolPangkat)
            i.putExtra("pendidikan", listItem.PendidikanTerakhir)
            i.putExtra("bidang", listItem.BidangKeahlian)
            i.putExtra("program", listItem.ProgramStudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = campuss(context)
            val alb = AlertDialog.Builder(context)
            val NIDN = holder.tvkodeNIDN!!.text
            val nama = holder.tvNamaDosen!!.text
            val program = holder.tvProgramStudi!!.text
            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda yakin akan menghapus ini?
                        $nama[$NIDN][$program]
                    """.trimIndent())
                setPositiveButton("Ya"){ _, _ ->
                    if(db.hapus("$NIDN"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNamaDosen: TextView? = null
        internal var tvkodeNIDN: TextView? = null
        internal var tvProgramStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}