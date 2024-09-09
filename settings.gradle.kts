rootProject.name = "CraftEssence"

// Include the subproject, using a unique name for the project
include("commonsessence")
include("warpessence")
include("core")
// Map the project name to the correct directory under 'essences'
project(":warpessence").projectDir = file("essences/warpessence")
project(":commonsessence").projectDir = file("essences/commonsessence")
