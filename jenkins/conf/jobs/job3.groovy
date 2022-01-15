#!groovy
println('------------------------------------------------------------------Import Job CaC/Job3')
def pipelineScript = new File('/var/jenkins_config/jobs/job3-pipeline.groovy').getText("UTF-8")

pipelineJob('CaC/Job3') {
    description("Job Pipline 3 - Ansible")
    /*parameters {    
        choice {
            name('action')
            choices(['apply', 'destroy'])
            description("apply or destroy")
        }
    }*/
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}