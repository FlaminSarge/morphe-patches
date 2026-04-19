package app.morphe.patches.youtube.misc.fix.pipfailureexit

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.youtube.misc.playservice.is_20_37_or_greater
import app.morphe.patches.youtube.misc.playservice.versionCheckPatch
import app.morphe.util.getReference
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val EXTENSION_CLASS =
    "Lapp/morphe/extension/youtube/patches/FixPipFailureExitPatch;"

internal val fixPipFailureExitPatch = bytecodePatch(
    description = "Fixes the app reinitializing when pressing back from the home screen with a video in the miniplayer."
) {
    dependsOn(versionCheckPatch)

    execute {
        if (!is_20_37_or_greater) return@execute

        // On PiP attempt failure, exit() finish()es the activity; replace with moveTaskToBack(true).
        PipFailureExitFingerprint.method.apply {
            val finishIndex = PipFailureExitFingerprint.instructionMatches.last().index
            val activityRegister =
                getInstruction<OneRegisterInstruction>(finishIndex - 1).registerA

            replaceInstruction(
                finishIndex,
                "invoke-static {v$activityRegister}, $EXTENSION_CLASS->moveToBackground(Landroid/app/Activity;)V"
            )
        }
    }
}
