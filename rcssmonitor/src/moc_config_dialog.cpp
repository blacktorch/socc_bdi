/****************************************************************************
** Meta object code from reading C++ file 'config_dialog.h'
**
** Created by: The Qt Meta Object Compiler version 63 (Qt 4.8.7)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "config_dialog.h"
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'config_dialog.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 63
#error "This file was generated using the moc from 4.8.7. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
static const uint qt_meta_data_FontButton[] = {

 // content:
       6,       // revision
       0,       // classname
       0,    0, // classinfo
       2,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       1,       // signalCount

 // signals: signature, parameters, type, tag, flags
      11,   25,   25,   25, 0x05,

 // slots: signature, parameters, type, tag, flags
      26,   25,   25,   25, 0x08,

       0        // eod
};

static const char qt_meta_stringdata_FontButton[] = {
    "FontButton\0fontChanged()\0\0fontDialog()\0"
};

void FontButton::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Q_ASSERT(staticMetaObject.cast(_o));
        FontButton *_t = static_cast<FontButton *>(_o);
        switch (_id) {
        case 0: _t->fontChanged(); break;
        case 1: _t->fontDialog(); break;
        default: ;
        }
    }
    Q_UNUSED(_a);
}

const QMetaObjectExtraData FontButton::staticMetaObjectExtraData = {
    0,  qt_static_metacall 
};

const QMetaObject FontButton::staticMetaObject = {
    { &QPushButton::staticMetaObject, qt_meta_stringdata_FontButton,
      qt_meta_data_FontButton, &staticMetaObjectExtraData }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &FontButton::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *FontButton::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *FontButton::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_FontButton))
        return static_cast<void*>(const_cast< FontButton*>(this));
    return QPushButton::qt_metacast(_clname);
}

int FontButton::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QPushButton::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 2)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 2;
    }
    return _id;
}

// SIGNAL 0
void FontButton::fontChanged()
{
    QMetaObject::activate(this, &staticMetaObject, 0, 0);
}
static const uint qt_meta_data_ConfigDialog[] = {

 // content:
       6,       // revision
       0,       // classname
       0,    0, // classinfo
      74,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       2,       // signalCount

 // signals: signature, parameters, type, tag, flags
      13,   26,   26,   26, 0x05,
      27,   48,   26,   26, 0x05,

 // slots: signature, parameters, type, tag, flags
      53,   26,   26,   26, 0x08,
      65,   86,   26,   26, 0x08,
      92,   26,   26,   26, 0x08,
     116,  144,   26,   26, 0x08,
     152,  144,   26,   26, 0x08,
     178,  144,   26,   26, 0x08,
     201,  144,   26,   26, 0x08,
     232,  144,   26,   26, 0x08,
     256,  144,   26,   26, 0x08,
     281,  144,   26,   26, 0x08,
     307,  144,   26,   26, 0x08,
     336,  144,   26,   26, 0x08,
     359,  144,   26,   26, 0x08,
     379,  144,   26,   26, 0x08,
     405,  144,   26,   26, 0x08,
     433,  144,   26,   26, 0x08,
     460,  144,   26,   26, 0x08,
     484,  144,   26,   26, 0x08,
     504,  144,   26,   26, 0x08,
     526,  144,   26,   26, 0x08,
     546,  144,   26,   26, 0x08,
     573,  144,   26,   26, 0x08,
     598,   86,   26,   26, 0x08,
     617,  639,   26,   26, 0x08,
     644,  144,   26,   26, 0x08,
     668,   26,   26,   26, 0x08,
     685,   26,   26,   26, 0x08,
     704,   26,   26,   26, 0x08,
     725,   26,   26,   26, 0x08,
     747,   26,   26,   26, 0x08,
     770,   26,   26,   26, 0x08,
     787,  805,   26,   26, 0x08,
     812,   86,   26,   26, 0x08,
     836,  871,   26,   26, 0x08,
     876,   26,   26,   26, 0x08,
     894,   26,   26,   26, 0x08,
     908,   26,   26,   26, 0x0a,
     927,   26,   26,   26, 0x0a,
     936,   26,   26,   26, 0x0a,
     946,   26,   26,   26, 0x0a,
     960,   26,   26,   26, 0x0a,
     978,   26,   26,   26, 0x0a,
    1001,   26,   26,   26, 0x0a,
    1026,   26,   26,   26, 0x0a,
    1050,   26,   26,   26, 0x0a,
    1067,   26,   26,   26, 0x0a,
    1086,   26,   26,   26, 0x0a,
    1103,   26,   26,   26, 0x0a,
    1128,   26,   26,   26, 0x0a,
    1151,   26,   26,   26, 0x0a,
    1171,   26,   26,   26, 0x0a,
    1199,   26,   26,   26, 0x0a,
    1220,   26,   26,   26, 0x0a,
    1242,   26,   26,   26, 0x0a,
    1265,   26,   26,   26, 0x0a,
    1291,   26,   26,   26, 0x0a,
    1311,   26,   26,   26, 0x0a,
    1328,   26,   26,   26, 0x0a,
    1352,   26,   26,   26, 0x0a,
    1373,  639,   26,   26, 0x0a,
    1395,  639,   26,   26, 0x0a,
    1419,   26,   26,   26, 0x0a,
    1437,   26,   26,   26, 0x0a,
    1457,   26,   26,   26, 0x0a,
    1471, 1493,   26,   26, 0x0a,
    1499,   26,   26,   26, 0x0a,
    1521,   26,   26,   26, 0x0a,
    1544,   26,   26,   26, 0x0a,
    1568,   26,   26,   26, 0x0a,
    1586,   26,   26,   26, 0x0a,
    1600,   26,   26,   26, 0x0a,
    1622,  805,   26,   26, 0x0a,

       0        // eod
};

static const char qt_meta_stringdata_ConfigDialog[] = {
    "ConfigDialog\0configured()\0\0"
    "canvasResized(QSize)\0size\0updateAll()\0"
    "slideFieldScale(int)\0value\0"
    "editFieldScale(QString)\0"
    "clickShowPlayerNumber(bool)\0checked\0"
    "clickShowPlayerType(bool)\0"
    "clickShowStamina(bool)\0"
    "clickShowStaminaCapacity(bool)\0"
    "clickShowViewArea(bool)\0"
    "clickShowCatchArea(bool)\0"
    "clickShowTackleArea(bool)\0"
    "clickShowKickAccelArea(bool)\0"
    "clickShowPointto(bool)\0clickShowCard(bool)\0"
    "clickShowScoreBoard(bool)\0"
    "clickShowKeepawayArea(bool)\0"
    "clickShowTeamGraphic(bool)\0"
    "clickShowDrawInfo(bool)\0clickShowBall(bool)\0"
    "clickShowPlayer(bool)\0clickShowFlag(bool)\0"
    "clickShowOffsideLine(bool)\0"
    "clickShowGridCoord(bool)\0slideGridStep(int)\0"
    "editGridStep(QString)\0text\0"
    "clickAntiAliasing(bool)\0clickFocusBall()\0"
    "clickFocusPlayer()\0clickSelectAutoAll()\0"
    "clickSelectAutoLeft()\0clickSelectAutoRight()\0"
    "clickSelectFix()\0choicePlayer(int)\0"
    "number\0changeBallVelCycle(int)\0"
    "selectColorEntry(QListWidgetItem*)\0"
    "item\0setDefaultColor()\0cancelColor()\0"
    "updateFieldScale()\0zoomIn()\0zoomOut()\0"
    "fitToScreen()\0applyCanvasSize()\0"
    "toggleShowScoreBoard()\0toggleShowKeepawayArea()\0"
    "toggleShowTeamGraphic()\0toggleShowFlag()\0"
    "toggleShowPlayer()\0toggleShowBall()\0"
    "toggleShowPlayerNumber()\0"
    "toggleShowPlayerType()\0toggleShowStamina()\0"
    "toggleShowStaminaCapacity()\0"
    "toggleShowViewArea()\0toggleShowCatchArea()\0"
    "toggleShowTackleArea()\0toggleShowKickAccelArea()\0"
    "toggleShowPointto()\0toggleShowCard()\0"
    "toggleShowOffsideLine()\0toggleShowDrawInfo()\0"
    "editBallSize(QString)\0editPlayerSize(QString)\0"
    "toggleFocusBall()\0toggleFocusPlayer()\0"
    "setFocusFix()\0setFocusPoint(QPoint)\0"
    "point\0toggleSelectAutoAll()\0"
    "toggleSelectAutoLeft()\0toggleSelectAutoRight()\0"
    "toggleSelectFix()\0setUnselect()\0"
    "selectPlayerWithKey()\0selectPlayer(int)\0"
};

void ConfigDialog::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        Q_ASSERT(staticMetaObject.cast(_o));
        ConfigDialog *_t = static_cast<ConfigDialog *>(_o);
        switch (_id) {
        case 0: _t->configured(); break;
        case 1: _t->canvasResized((*reinterpret_cast< const QSize(*)>(_a[1]))); break;
        case 2: _t->updateAll(); break;
        case 3: _t->slideFieldScale((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 4: _t->editFieldScale((*reinterpret_cast< const QString(*)>(_a[1]))); break;
        case 5: _t->clickShowPlayerNumber((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 6: _t->clickShowPlayerType((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 7: _t->clickShowStamina((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 8: _t->clickShowStaminaCapacity((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 9: _t->clickShowViewArea((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 10: _t->clickShowCatchArea((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 11: _t->clickShowTackleArea((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 12: _t->clickShowKickAccelArea((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 13: _t->clickShowPointto((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 14: _t->clickShowCard((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 15: _t->clickShowScoreBoard((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 16: _t->clickShowKeepawayArea((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 17: _t->clickShowTeamGraphic((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 18: _t->clickShowDrawInfo((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 19: _t->clickShowBall((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 20: _t->clickShowPlayer((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 21: _t->clickShowFlag((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 22: _t->clickShowOffsideLine((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 23: _t->clickShowGridCoord((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 24: _t->slideGridStep((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 25: _t->editGridStep((*reinterpret_cast< const QString(*)>(_a[1]))); break;
        case 26: _t->clickAntiAliasing((*reinterpret_cast< bool(*)>(_a[1]))); break;
        case 27: _t->clickFocusBall(); break;
        case 28: _t->clickFocusPlayer(); break;
        case 29: _t->clickSelectAutoAll(); break;
        case 30: _t->clickSelectAutoLeft(); break;
        case 31: _t->clickSelectAutoRight(); break;
        case 32: _t->clickSelectFix(); break;
        case 33: _t->choicePlayer((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 34: _t->changeBallVelCycle((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 35: _t->selectColorEntry((*reinterpret_cast< QListWidgetItem*(*)>(_a[1]))); break;
        case 36: _t->setDefaultColor(); break;
        case 37: _t->cancelColor(); break;
        case 38: _t->updateFieldScale(); break;
        case 39: _t->zoomIn(); break;
        case 40: _t->zoomOut(); break;
        case 41: _t->fitToScreen(); break;
        case 42: _t->applyCanvasSize(); break;
        case 43: _t->toggleShowScoreBoard(); break;
        case 44: _t->toggleShowKeepawayArea(); break;
        case 45: _t->toggleShowTeamGraphic(); break;
        case 46: _t->toggleShowFlag(); break;
        case 47: _t->toggleShowPlayer(); break;
        case 48: _t->toggleShowBall(); break;
        case 49: _t->toggleShowPlayerNumber(); break;
        case 50: _t->toggleShowPlayerType(); break;
        case 51: _t->toggleShowStamina(); break;
        case 52: _t->toggleShowStaminaCapacity(); break;
        case 53: _t->toggleShowViewArea(); break;
        case 54: _t->toggleShowCatchArea(); break;
        case 55: _t->toggleShowTackleArea(); break;
        case 56: _t->toggleShowKickAccelArea(); break;
        case 57: _t->toggleShowPointto(); break;
        case 58: _t->toggleShowCard(); break;
        case 59: _t->toggleShowOffsideLine(); break;
        case 60: _t->toggleShowDrawInfo(); break;
        case 61: _t->editBallSize((*reinterpret_cast< const QString(*)>(_a[1]))); break;
        case 62: _t->editPlayerSize((*reinterpret_cast< const QString(*)>(_a[1]))); break;
        case 63: _t->toggleFocusBall(); break;
        case 64: _t->toggleFocusPlayer(); break;
        case 65: _t->setFocusFix(); break;
        case 66: _t->setFocusPoint((*reinterpret_cast< const QPoint(*)>(_a[1]))); break;
        case 67: _t->toggleSelectAutoAll(); break;
        case 68: _t->toggleSelectAutoLeft(); break;
        case 69: _t->toggleSelectAutoRight(); break;
        case 70: _t->toggleSelectFix(); break;
        case 71: _t->setUnselect(); break;
        case 72: _t->selectPlayerWithKey(); break;
        case 73: _t->selectPlayer((*reinterpret_cast< int(*)>(_a[1]))); break;
        default: ;
        }
    }
}

const QMetaObjectExtraData ConfigDialog::staticMetaObjectExtraData = {
    0,  qt_static_metacall 
};

const QMetaObject ConfigDialog::staticMetaObject = {
    { &QDialog::staticMetaObject, qt_meta_stringdata_ConfigDialog,
      qt_meta_data_ConfigDialog, &staticMetaObjectExtraData }
};

#ifdef Q_NO_DATA_RELOCATION
const QMetaObject &ConfigDialog::getStaticMetaObject() { return staticMetaObject; }
#endif //Q_NO_DATA_RELOCATION

const QMetaObject *ConfigDialog::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->metaObject : &staticMetaObject;
}

void *ConfigDialog::qt_metacast(const char *_clname)
{
    if (!_clname) return 0;
    if (!strcmp(_clname, qt_meta_stringdata_ConfigDialog))
        return static_cast<void*>(const_cast< ConfigDialog*>(this));
    return QDialog::qt_metacast(_clname);
}

int ConfigDialog::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QDialog::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 74)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 74;
    }
    return _id;
}

// SIGNAL 0
void ConfigDialog::configured()
{
    QMetaObject::activate(this, &staticMetaObject, 0, 0);
}

// SIGNAL 1
void ConfigDialog::canvasResized(const QSize & _t1)
{
    void *_a[] = { 0, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 1, _a);
}
QT_END_MOC_NAMESPACE
