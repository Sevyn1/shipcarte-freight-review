const form = document.querySelector('#calculator');
const button = document.querySelector('#calculate');
const result = document.querySelector('#result');
const error = document.querySelector('#error');
const examples = {
  imperial: ['12','12','12','10','inch','lbs'],
  metric: ['30.48','30.48','30.48','4.5359237','cm','kg'],
  larger: ['24','12','12','10','inch','lbs'],
};
let controller;
function clearResult() {
  result.replaceChildren();
  const hint = document.createElement('p');
  hint.className = 'placeholder'; hint.textContent = 'Measurements changed. Calculate to update.';
  result.append(hint); error.hidden = true;
}
form.addEventListener('input', clearResult);
form.addEventListener('change', clearResult);
document.querySelectorAll('[data-example]').forEach(sample => sample.addEventListener('click', () => {
  ['length','breadth','height','weight','lengthUnit','weightUnit'].forEach((name,i) => {
    form.elements.namedItem(name).value = examples[sample.dataset.example][i];
  });
  clearResult(); form.requestSubmit();
}));
form.addEventListener('submit', async event => {
  event.preventDefault();
  controller?.abort();
  const active = new AbortController(); controller = active;
  button.disabled = true; button.textContent = 'Calculating…';
  error.hidden = true; result.textContent = 'Calculating density…';
  const inputs = [...form.querySelectorAll('input,select')];
  const sampleButtons = [...document.querySelectorAll('[data-example]')];
  [...inputs,...sampleButtons].forEach(input => input.disabled = true);
  // Capture disabled fields explicitly: FormData omits them.
  const parameters = new URLSearchParams();
  inputs.forEach(input => parameters.set(input.name, input.value));
  let timedOut = false;
  const timer = setTimeout(() => { timedOut = true; active.abort(); }, 10000);
  try {
    const response = await fetch(`/api/density?${parameters}`, {signal:active.signal,cache:'no-store'});
    const data = await response.json();
    if (!response.ok) throw new Error(data.error || 'Check your measurements and try again.');
    if (!Number.isFinite(data.density) || data.density <= 0) throw new Error('The calculator returned an invalid result.');
    const value = document.createElement('p'); value.className = 'density';
    value.textContent = new Intl.NumberFormat('en-CA', {maximumSignificantDigits:7}).format(data.density);
    const unit = document.createElement('p'); unit.className = 'density-unit'; unit.textContent = 'lb / ft³';
    result.replaceChildren(value,unit);
  } catch (failure) {
    result.textContent = 'No density calculated.';
    error.textContent = timedOut ? 'The calculator timed out. Please try again.' : failure instanceof TypeError ? 'Cannot reach the calculator. Please try again.' : failure.message;
    error.hidden = false;
  } finally {
    clearTimeout(timer); button.disabled = false; button.textContent = 'Calculate density →';
    [...inputs,...sampleButtons].forEach(input => input.disabled = false);
  }
});
