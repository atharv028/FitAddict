package com.tare.fitaddict.pojo.entities


import com.google.gson.annotations.SerializedName

data class TotalNutrients(
    @SerializedName("CA")
    val cA: CAX = CAX(),
    @SerializedName("CHOCDF")
    val cHOCDF: CHOCDFX = CHOCDFX(),
    @SerializedName("CHOCDF.net")
    val cHOCDFNet: CHOCDFNet = CHOCDFNet(),
    @SerializedName("CHOLE")
    val cHOLE: CHOLEX = CHOLEX(),
    @SerializedName("ENERC_KCAL")
    val eNERCKCAL: ENERCKCALX = ENERCKCALX(),
    @SerializedName("FAMS")
    val fAMS: FAMS = FAMS(),
    @SerializedName("FAPU")
    val fAPU: FAPU = FAPU(),
    @SerializedName("FASAT")
    val fASAT: FASATX = FASATX(),
    @SerializedName("FAT")
    val fAT: FATX = FATX(),
    @SerializedName("FATRN")
    val fATRN: FATRN = FATRN(),
    @SerializedName("FE")
    val fE: FEX = FEX(),
    @SerializedName("FIBTG")
    val fIBTG: FIBTGX = FIBTGX(),
    @SerializedName("FOLAC")
    val fOLAC: FOLAC = FOLAC(),
    @SerializedName("FOLDFE")
    val fOLDFE: FOLDFEX = FOLDFEX(),
    @SerializedName("FOLFD")
    val fOLFD: FOLFD = FOLFD(),
    @SerializedName("K")
    val k: KX = KX(),
    @SerializedName("MG")
    val mG: MGX = MGX(),
    @SerializedName("NA")
    val nA: NAX = NAX(),
    @SerializedName("NIA")
    val nIA: NIAX = NIAX(),
    @SerializedName("P")
    val p: PX = PX(),
    @SerializedName("PROCNT")
    val pROCNT: PROCNTX = PROCNTX(),
    @SerializedName("RIBF")
    val rIBF: RIBFX = RIBFX(),
    @SerializedName("SUGAR")
    val sUGAR: SUGAR = SUGAR(),
    @SerializedName("SUGAR.added")
    val sUGARAdded: SUGARAdded = SUGARAdded(),
    @SerializedName("Sugar.alcohol")
    val sugarAlcohol: SugarAlcohol = SugarAlcohol(),
    @SerializedName("THIA")
    val tHIA: THIAX = THIAX(),
    @SerializedName("TOCPHA")
    val tOCPHA: TOCPHAX = TOCPHAX(),
    @SerializedName("VITA_RAE")
    val vITARAE: VITARAEX = VITARAEX(),
    @SerializedName("VITB12")
    val vITB12: VITB12X = VITB12X(),
    @SerializedName("VITB6A")
    val vITB6A: VITB6AX = VITB6AX(),
    @SerializedName("VITC")
    val vITC: VITCX = VITCX(),
    @SerializedName("VITD")
    val vITD: VITDX = VITDX(),
    @SerializedName("VITK1")
    val vITK1: VITK1X = VITK1X(),
    @SerializedName("WATER")
    val wATER: WATER = WATER(),
    @SerializedName("ZN")
    val zN: ZNX = ZNX()
)