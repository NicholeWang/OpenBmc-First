SUMMARY = "eCMD"
DESCRIPTION = "eCMD is a hardware access API for IBM Systems"
LICENSE= "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/NOTICE;md5=fee220301a2af3faf8f211524b4248ea"

SRC_URI = "git://github.com/open-power/eCMD.git"
SRCREV = "b858b2f212bda2665ad41e821302e8196c7aec07"
DEPENDS += "python-native zlib"

SRC_URI += "file://croserver.service"

S = "${WORKDIR}/git"

do_configure() {
   LD="${CXX}" ${S}/config.py --without-swig --output-root ${B} --target obj --extensions "cmd cip" --build-verbose
}

do_compile() {
    cd ${S}/dllNetwork/server
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 out_obj/lib/server1p ${D}${bindir}/croserver

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/croserver.service ${D}${systemd_system_unitdir}/
}

FILES_${PN} += "${systemd_system_unitdir}/croserver.service"