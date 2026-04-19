package app.morphe.patches.youtube.misc.fix.pipfailureexit

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.InstructionLocation.MatchAfterWithin
import app.morphe.patcher.literal
import app.morphe.patcher.methodCall
import com.android.tools.smali.dexlib2.Opcode

// Matches the PiP failure exit method via the feature flag literal and finish() call.
internal object PipFailureExitFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf(),
    filters = listOf(
        literal(1073815111L),
        methodCall(opcode = Opcode.INVOKE_VIRTUAL, name = "finish", location = MatchAfterWithin(5))
    )
)
