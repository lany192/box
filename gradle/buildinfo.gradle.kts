val beginOfSetting = System.currentTimeMillis()

gradle.settingsEvaluated {
    println("初始化阶段0 settingsEvaluated")
}
gradle.projectsLoaded {
    println("----------------------初始化阶段----------------------")
    println("初始化阶段，耗时：" + (System.currentTimeMillis() - beginOfSetting) + "ms")
    println("----------------------初始化阶段----------------------")
}

gradle.beforeProject {
    println("配置阶段0 beforeProject")
}
gradle.afterProject {
    println("配置阶段1 afterProject")
}

gradle.projectsEvaluated {
    println("配置阶段2 projectsEvaluated")
}
gradle.taskGraph.whenReady {
    println("配置阶段3 taskGraph.whenReady")
}
