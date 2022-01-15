#!groovy
println('------------------------------------------------------------------Import Job IaC/Job2')
def pipelineScript = new File('/var/jenkins_config/jobs/job2-pipeline.groovy').getText("UTF-8")

pipelineJob('IaC/Job2') {
    description("Job Pipline 2 - Terraform")
    parameters {    
        choice {
            name('action')
            choices(['apply', 'destroy'])
            description("apply or destroy")
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}