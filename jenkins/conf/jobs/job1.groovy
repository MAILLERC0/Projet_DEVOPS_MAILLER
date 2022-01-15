#!groovy
println('------------------------------------------------------------------Import Job CI/Job1')
def pipelineScript = new File('/var/jenkins_config/jobs/job1-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Job1') {
    description("Job Pipline 1")
    parameters {
        stringParam {
            name('BRANCH')
            defaultValue('master')
            description("String pour sélectionner la branche du repo Github")
        }
        booleanParam {
            name('SKIP_TESTS')
            defaultValue(false)
            description("un booléen pour contrôler l'exécution des tests")
        }
        choice {
            name('VERSION_TYPE')
            choices(['SNAPSHOT', 'RELEASE'])
            description("SNAPSHOT ou RELEASE")
        }
        stringParam {
            name('VERSION')
            defaultValue('SB3T-1.0')
            description("un string pour la version du jar")
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}