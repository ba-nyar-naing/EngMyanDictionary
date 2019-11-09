package com.s16.utils

object RabbitConverter {

    private val unicodeToZawgyiRules: Map<String, String> = mapOf(
        "\u1004\u103a\u1039" to "\u1064",
        "\u1039\u1010\u103d" to "\u1096",
        "\u102b\u103a" to "\u105a",
        "\u102d\u1036" to "\u108e",
        "\u104e\u1004\u103a\u1038" to "\u104e",
        "[\u1025\u1009](?=\u1039)" to "\u106a",
        "\u1009(?=[\u102f\u1030])" to "\u1025",
        "[\u1025\u1009](?=[\u1037]?[\u103a])" to "\u1025",
        "\u100a(?=[\u1039\u103d])" to "\u106b",
        "(\u1039[\u1000-\u1021])(\u102D){0,1}\u102f" to "$1$2\u1033",
        "(\u1039[\u1000-\u1021])\u1030" to "$1\u1034",
        "\u1014(?=[\u102d\u102e\u102f\u103A]?[\u1030\u103d\u103e\u102f\u1039])" to "\u108f",
        "\u1014(?=\u103A\u102F )" to "\u108f",
        "\u1014\u103c" to "\u108f\u103c",
        "\u1039\u1000" to "\u1060",
        "\u1039\u1001" to "\u1061",
        "\u1039\u1002" to "\u1062",
        "\u1039\u1003" to "\u1063",
        "\u1039\u1005" to "\u1065",
        "\u1039\u1006" to "\u1066",
        "\u1039\u1007" to "\u1068",
        "\u1039\u1008" to "\u1069",
        "\u1039\u100b" to "\u106c",
        "\u100b\u1039\u100c" to "\u1092",
        "\u1039\u100c" to "\u106d",
        "\u100d\u1039\u100d" to "\u106e",
        "\u100d\u1039\u100e" to "\u106f",
        "\u1039\u100f" to "\u1070",
        "\u1039\u1010" to "\u1071",
        "\u1039\u1011" to "\u1073",
        "\u1039\u1012" to "\u1075",
        "\u1039\u1013" to "\u1076",
        "\u1039[\u1014\u108f]" to "\u1077",
        "\u1039\u1015" to "\u1078",
        "\u1039\u1016" to "\u1079",
        "\u1039\u1017" to "\u107a",
        "\u1039\u1018" to "\u107b",
        "\u1039\u1019" to "\u107c",
        "\u1039\u101c" to "\u1085",
        "\u103f" to "\u1086",
        "\u103d\u103e" to "\u108a",
        "(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u102d" to "$2$3\u108b",
        "(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u102e" to "$2$3\u108c",
        "(\u1064)([\u1000-\u1021])([\u103b\u103c]?)\u1036" to "$2$3\u108d",
        "(\u1064)([\u1000-\u1021\u1040-\u1049])([\u103b\u103c]?)([\u1031]?)" to "$2$3$4$1",
        "\u101b(?=([\u102d\u102e]?)[\u102f\u1030\u103d\u108a])" to "\u1090",
        "\u100f\u1039\u100d" to "\u1091",
        "\u100b\u1039\u100b" to "\u1097",
        "([\u1000-\u1021\u108f\u1029\u106e\u106f\u1086\u1090\u1091\u1092\u1097])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085\u108a])?([\u103b-\u103e]*)?\u1031" to "\u1031$1$2$3",
        "\u103c\u103e" to "\u103c\u1087",
        "([\u1000-\u1021\u108f\u1029])([\u1060-\u1069\u106c\u106d\u1070-\u107c\u1085])?(\u103c)" to "$3$1$2",
        "\u103a" to "\u1039",
        "\u103b" to "\u103a",
        "\u103c" to "\u103b",
        "\u103d" to "\u103c",
        "\u103e" to "\u103d",
        "([^\u103a\u100a])\u103d([\u102d\u102e]?)\u102f" to "$1\u1088$2",
        "([\u101b\u103a\u103c\u108a\u1088\u1090])([\u1030\u103d])?([\u1032\u1036\u1039\u102d\u102e\u108b\u108c\u108d\u108e]?)(\u102f)?\u1037" to "$1$2$3$4\u1095",
        "([\u102f\u1014\u1030\u103d])([\u1032\u1036\u1039\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1037" to "$1$2\u1094",
        "([\u103b])([\u1000-\u1021])([\u1087]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u102f" to "$1$2$3$4\u1033",
        "([\u103b])([\u1000-\u1021])([\u1087]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1030" to "$1$2$3$4\u1034",
        "([\u103a\u103c\u100a\u1008\u100b\u100c\u100d\u1020\u1025])([\u103d]?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u102f" to "$1$2$3\u1033",
        "([\u103a\u103c\u100a\u1008\u100b\u100c\u100d\u1020\u1025])(\u103d?)([\u1036\u102d\u102e\u108b\u108c\u108d\u108e]?)\u1030" to "$1$2$3\u1034",
        "([\u100a\u1020\u1009])\u103d" to "$1\u1087",
        "\u103d\u1030" to "\u1089",
        "\u103b([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])" to "\u107e$1",
        "\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])" to "\u1084$1$2$3",
        "\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u103c\u108a])" to "\u1082$1$2",
        "\u107e([\u1000\u1003\u1006\u100f\u1010\u1011\u1018\u101a\u101c\u101a\u101e\u101f])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])" to "\u1080$1$2$3",
        "\u103b([\u1000-\u1021])([\u103c\u108a])([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])" to "\u1083$1$2$3",
        "\u103b([\u1000-\u1021])([\u103c\u108a])" to "\u1081$1$2",
        "\u103b([\u1000-\u1021])([\u1033\u1034]?)([\u1032\u1036\u102d\u102e\u108b\u108c\u108d\u108e])" to "\u107f$1$2$3",
        "\u103a\u103d" to "\u103d\u103a",
        "\u103a([\u103c\u108a])" to "$1\u107d",
        "([\u1033\u1034])(\u1036?)\u1094" to "$1$2\u1095",
        "\u108F\u1071" to "\u108F\u1072",
        "([\u1000-\u1021])([\u107B\u1066])\u102C" to "$1\u102C$2",
        "\u102C([\u107B\u1066])\u1037" to "\u102C$1\u1094",
        "\u1047((?=[\u1000-\u1021]\u1039)|(?=[\u102c-\u1030\u1032\u1036-\u1038\u103c\u103d]))" to "\u101b"
    )

    private val zawgyiToUnicodeRules: Map<String, String> = mapOf(
        "([\u102D\u102E\u103D\u102F\u1037\u1095])\\1+" to "$1" ,
        "\u200B" to "" ,
        "\u103d\u103c" to "\u108a" ,
        "(\u103d|\u1087)" to "\u103e" ,
        "\u103c" to "\u103d" ,
        "(\u103b|\u107e|\u107f|\u1080|\u1081|\u1082|\u1083|\u1084)" to "\u103c" ,
        "(\u103a|\u107d)" to "\u103b" ,
        "\u1039" to "\u103a" ,
        "(\u1066|\u1067)" to "\u1039\u1006" ,
        "\u106a" to "\u1009" ,
        "\u106b" to "\u100a" ,
        "\u106c" to "\u1039\u100b" ,
        "\u106d" to "\u1039\u100c" ,
        "\u106e" to "\u100d\u1039\u100d" ,
        "\u106f" to "\u100d\u1039\u100e" ,
        "\u1070" to "\u1039\u100f" ,
        "(\u1071|\u1072)" to "\u1039\u1010" ,
        "\u1060" to "\u1039\u1000" ,
        "\u1061" to "\u1039\u1001" ,
        "\u1062" to "\u1039\u1002" ,
        "\u1063" to "\u1039\u1003" ,
        "\u1065" to "\u1039\u1005" ,
        "\u1068" to "\u1039\u1007" ,
        "\u1069" to "\u1039\u1008" ,
        "(\u1073|\u1074)" to "\u1039\u1011" ,
        "\u1075" to "\u1039\u1012" ,
        "\u1076" to "\u1039\u1013" ,
        "\u1077" to "\u1039\u1014" ,
        "\u1078" to "\u1039\u1015" ,
        "\u1079" to "\u1039\u1016" ,
        "\u107a" to "\u1039\u1017" ,
        "\u107c" to "\u1039\u1019" ,
        "\u1085" to "\u1039\u101c" ,
        "\u1033" to "\u102f" ,
        "\u1034" to "\u1030" ,
        "\u103f" to "\u1030" ,
        "\u1086" to "\u103f" ,
        "\u1036\u1088" to "\u1088\u1036" ,
        "\u1088" to "\u103e\u102f" ,
        "\u1089" to "\u103e\u1030" ,
        "\u108a" to "\u103d\u103e" ,
        "\u103B\u1064" to "\u1064\u103B" ,
        "\u103c([\u1000-\u1021])(\u1064|\u108b)" to "$1\u103c$2" ,
        "(\u1031)?([\u1000-\u1021\u1040-\u1049])(\u103c)?\u1064" to "\u1004\u103a\u1039$1$2$3" ,
        "(\u1031)?([\u1000-\u1021])(\u103b|\u103c)?\u108b" to "\u1004\u103a\u1039$1$2$3\u102d" ,
        "(\u1031)?([\u1000-\u1021])(\u103b)?\u108c" to "\u1004\u103a\u1039$1$2$3\u102e" ,
        "(\u1031)?([\u1000-\u1021])(\u103b)?\u108d" to "\u1004\u103a\u1039$1$2$3\u1036" ,
        "\u108e" to "\u102d\u1036" ,
        "\u108f" to "\u1014" ,
        "\u1090" to "\u101b" ,
        "\u1091" to "\u100f\u1039\u100d" ,
        "\u1092" to "\u100b\u1039\u100c" ,
        "\u1019\u102c(\u107b|\u1093)" to "\u1019\u1039\u1018\u102c" ,
        "(\u107b|\u1093)" to "\u1039\u1018" ,
        "(\u1094|\u1095)" to "\u1037" ,
        "([\u1000-\u1021])\u1037\u1032" to "$1\u1032\u1037" ,
        "\u1096" to "\u1039\u1010\u103d" ,
        "\u1097" to "\u100b\u1039\u100b" ,
        "\u103c([\u1000-\u1021])([\u1000-\u1021])?" to "$1\u103c$2" ,
        "([\u1000-\u1021])\u103c\u103a" to "\u103c$1\u103a" ,
        "\u1047(?=[\u102c-\u1030\u1032\u1036-\u1038\u103d\u1038])" to "\u101b" ,
        "\u1031\u1047" to "\u1031\u101b" ,
        "\u1040(\u102e|\u102f|\u102d\u102f|\u1030|\u1036|\u103d|\u103e)" to "\u101d$1" ,
        "([^\u1040\u1041\u1042\u1043\u1044\u1045\u1046\u1047\u1048\u1049])\u1040\u102b" to "$1\u101d\u102b" ,
        "([\u1040\u1041\u1042\u1043\u1044\u1045\u1046\u1047\u1048\u1049])\u1040\u102b(?!\u1038)" to "$1\u101d\u102b" ,
        "^\u1040(?=\u102b)" to "\u101d" ,
        "\u1040\u102d(?!\u0020?/)" to "\u101d\u102d" ,
        "([^\u1040-\u1049])\u1040([^\u1040-\u1049\u0020]|[\u104a\u104b])" to "$1\u101d$2" ,
        "([^\u1040-\u1049])\u1040(?=[\\f\\n\\r])" to "$1\u101d" ,
        "([^\u1040-\u1049])\u1040$" to "$1\u101d" ,
        "\u1031([\u1000-\u1021\u103f])(\u103e)?(\u103b)?" to "$1$2$3\u1031" ,
        "([\u1000-\u1021])\u1031([\u103b\u103c\u103d\u103e]+)" to "$1$2\u1031" ,
        "\u1032\u103d" to "\u103d\u1032" ,
        "([\u102d\u102e])\u103b" to "\u103b$1" ,
        "\u103d\u103b" to "\u103b\u103d" ,
        "\u103a\u1037" to "\u1037\u103a" ,
        "\u102f(\u102d|\u102e|\u1036|\u1037)\u102f" to "\u102f$1" ,
        "(\u102f|\u1030)(\u102d|\u102e)" to "$2$1" ,
        "(\u103e)(\u103b|\u103c)" to "$2$1" ,
        "\u1025(?=[\u1037]?[\u103a\u102c])" to "\u1009" ,
        "\u1025\u102e" to "\u1026" ,
        "\u1005\u103b" to "\u1008" ,
        "\u1036(\u102f|\u1030)" to "$1\u1036" ,
        "\u1031\u1037\u103e" to "\u103e\u1031\u1037" ,
        "\u1031\u103e\u102c" to "\u103e\u1031\u102c" ,
        "\u105a" to "\u102b\u103a" ,
        "\u1031\u103b\u103e" to "\u103b\u103e\u1031" ,
        "(\u102d|\u102e)(\u103d|\u103e)" to "$2$1" ,
        "\u102c\u1039([\u1000-\u1021])" to "\u1039$1\u102c" ,
        "\u1039\u103c\u103a\u1039([\u1000-\u1021])" to "\u103a\u1039$1\u103c" ,
        "\u103c\u1039([\u1000-\u1021])" to "\u1039$1\u103c" ,
        "\u1036\u1039([\u1000-\u1021])" to "\u1039$1\u1036" ,
        "\u104e" to "\u104e\u1004\u103a\u1038" ,
        "\u1040(\u102b|\u102c|\u1036)" to "\u101d$1" ,
        "\u1025\u1039" to "\u1009\u1039" ,
        "([\u1000-\u1021])\u103c\u1031\u103d" to "$1\u103c\u103d\u1031" ,
        "([\u1000-\u1021])\u103b\u1031\u103d(\u103e)?" to "$1\u103b\u103d$2\u1031" ,
        "([\u1000-\u1021])\u103d\u1031\u103b" to "$1\u103b\u103d\u1031" ,
        "([\u1000-\u1021])\u1031(\u1039[\u1000-\u1021])" to "$1$2\u1031" ,
        "\u1038\u103a" to "\u103a\u1038" ,
        "\u102d\u103a|\u103a\u102d" to "\u102d" ,
        "\u102d\u102f\u103a" to "\u102d\u102f" ,
        "\u0020\u1037" to "\u1037" ,
        "\u1037\u1036" to "\u1036\u1037" ,
        "[\u102d]+" to "\u102d" ,
        "[\u103a]+" to "\u103a" ,
        "[\u103d]+" to "\u103d" ,
        "[\u1037]+" to "\u1037" ,
        "[\u102e]+" to "\u102e" ,
        "\u102d\u102e|\u102e\u102d" to "\u102e" ,
        "\u102f\u102d" to "\u102d\u102f" ,
        "\u1037\u1037" to "\u1037" ,
        "\u1032\u1032" to "\u1032" ,
        "\u1044\u1004\u103a\u1038" to "\u104E\u1004\u103a\u1038" ,
        "([\u102d\u102e])\u1039([\u1000-\u1021])" to "\u1039$2$1" ,
        "(\u103c\u1031)\u1039([\u1000-\u1021])" to "\u1039$2$1" ,
        "\u1036\u103d" to "\u103d\u1036" ,
        "\u1047((?=[\u1000-\u1021]\u103a)|(?=[\u102c-\u1030\u1032\u1036-\u1038\u103d\u103e]))" to "\u101b"
    )

    @JvmStatic
    fun uni2zg(input: String): String {
        return replaceWithRule(unicodeToZawgyiRules, input)
    }

    @JvmStatic
    fun zg2uni(input: String): String {
        return replaceWithRule(zawgyiToUnicodeRules, input)
    }

    private fun replaceWithRule(rules: Map<String, String>, input: String): String {
        var output = input
        rules.forEach { (from, to) ->
            output = output.replace(from.toRegex(), to)
        }
        return output.replace("null", "")
    }

}